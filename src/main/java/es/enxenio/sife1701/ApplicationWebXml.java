package es.enxenio.sife1701;

import es.enxenio.sife1701.config.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ApplicationWebXml extends SpringBootServletInitializer {

    private final Logger log = LoggerFactory.getLogger(ApplicationWebXml.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.profiles(addDefaultProfile())
            .sources(Application.class);
    }

    /**
     * Set a default profile if it has not been set.
     * <p>
     * Please use -Dspring.profiles.active=dev
     * </p>
     */
    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null) {
            log.info("Running with Spring profile(s) : {}", profile);
            return profile;
        }

        log.warn("No Spring profile configured, running with default configuracion");
        return Constants.SPRING_PROFILE_PRODUCTION;
    }
}
