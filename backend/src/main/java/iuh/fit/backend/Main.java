package iuh.fit.backend;

import iuh.fit.backend.config.JPAUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static HttpServer startServer() {
        String portStr = System.getenv("PORT");
        int port = (portStr != null && !portStr.isBlank()) ? Integer.parseInt(portStr) : 8080;
        String baseUri = "http://0.0.0.0:" + port + "/";

        HelloApplication config = new HelloApplication();
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), config, false);
        
        try {
            server.start();
            LOGGER.info("MeMeStudy Backend started at " + baseUri + "api");
            LOGGER.info("Health check: " + baseUri + "api/health");
            LOGGER.info("Rounds list:  " + baseUri + "api/rounds");
        } catch (IOException e) {
            LOGGER.severe("Failed to start HTTP server: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Initialize JPA connection pool
        try {
            JPAUtil.getEntityManagerFactory();
        } catch (Exception e) {
            LOGGER.warning("Could not pre-initialize JPA: " + e.getMessage());
        }

        return server;
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Application started.\nPress Ctrl+C to stop the server...");
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping server...");
            server.shutdownNow();
            JPAUtil.close();
        }));

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

