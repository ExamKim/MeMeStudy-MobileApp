package iuh.fit.backend.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {
    private static final Logger LOGGER = Logger.getLogger(JPAUtil.class.getName());
    private static EntityManagerFactory emf;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            initEntityManagerFactory();
        }
        return emf;
    }

    private static void initEntityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");

        if (dbUrl == null || dbUrl.isBlank()) {
            dbUrl = "jdbc:mariadb://localhost:3306/memestudy?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8";
        }
        if (dbUser == null) {
            dbUser = "memeuser";
        }
        if (dbPass == null) {
            dbPass = "memepassword";
        }

        // Check if target MariaDB is reachable
        boolean canConnect = false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                canConnect = true;
                LOGGER.info("Successfully connected to database at: " + dbUrl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Cannot connect to MariaDB (" + e.getMessage() + "). Falling back to embedded H2 database for dev/testing.");
        }

        if (canConnect) {
            properties.put("jakarta.persistence.jdbc.driver", "org.mariadb.jdbc.Driver");
            properties.put("jakarta.persistence.jdbc.url", dbUrl);
            properties.put("jakarta.persistence.jdbc.user", dbUser);
            properties.put("jakarta.persistence.jdbc.password", dbPass);
            properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
            properties.put("hibernate.hbm2ddl.auto", "update");
        } else {
            // H2 Fallback
            properties.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
            properties.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:memestudy;DB_CLOSE_DELAY=-1;MODE=MySQL");
            properties.put("jakarta.persistence.jdbc.user", "sa");
            properties.put("jakarta.persistence.jdbc.password", "");
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.put("hibernate.hbm2ddl.auto", "create-drop");
        }

        try {
            emf = Persistence.createEntityManagerFactory("memestudyPU", properties);
            LOGGER.info("EntityManagerFactory initialized successfully.");
            
            // If using H2 in-memory or empty DB, automatically run seed if needed
            if (!canConnect) {
                seedInitialDataIfEmpty();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to create EntityManagerFactory", e);
            throw new RuntimeException("Could not initialize JPA", e);
        }
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private static void seedInitialDataIfEmpty() {
        try {
            iuh.fit.backend.util.DatabaseSeeder.seedIfEmpty();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not auto-seed database: " + e.getMessage());
        }
    }
}

