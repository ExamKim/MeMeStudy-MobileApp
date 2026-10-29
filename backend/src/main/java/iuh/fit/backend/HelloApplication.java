package iuh.fit.backend;

import iuh.fit.backend.exception.GlobalExceptionMapper;
import iuh.fit.backend.filter.CorsFilter;
import iuh.fit.backend.resource.HealthResource;
import iuh.fit.backend.resource.RoundResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Register resources
        register(HealthResource.class);
        register(RoundResource.class);

        // Register providers and filters
        register(CorsFilter.class);
        register(GlobalExceptionMapper.class);
        register(JacksonFeature.class);

        // Scan package for other resources if added
        packages("iuh.fit.backend.resource", "iuh.fit.backend.filter", "iuh.fit.backend.exception");
    }
}
