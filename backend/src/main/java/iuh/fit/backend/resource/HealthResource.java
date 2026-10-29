package iuh.fit.backend.resource;

import iuh.fit.backend.config.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    public Response checkHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());

        boolean dbUp = false;
        try {
            EntityManager em = JPAUtil.getEntityManager();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.close();
            dbUp = true;
        } catch (Exception e) {
            health.put("db_error", e.getMessage());
        }

        health.put("database", dbUp ? "CONNECTED" : "DISCONNECTED");

        return Response.ok(health).build();
    }
}

