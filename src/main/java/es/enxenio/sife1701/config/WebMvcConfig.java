package es.enxenio.sife1701.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Esta clase permite activar el modo html5 en Angular para que funcione correctamente al recargar la página.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ResourceProperties resourceProperties = new ResourceProperties();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Integer cachePeriod = resourceProperties.getCachePeriod();

        final String[] staticLocations = resourceProperties.getStaticLocations();
        final String[] indexLocations = new String[staticLocations.length];
        for (int i = 0; i < staticLocations.length; i++) {
            indexLocations[i] = staticLocations[i] + "index.html";
        }
        registry.addResourceHandler(
            "/**/*.css",
            "/**/*.html",
            "/**/*.js",
            "/**/*.json",
            "/**/*.ttf",
            "/**/*.eot",
            "/**/*.bmp",
            "/**/*.svg",
            "/**/*.woff",
            "/**/*.woff2",

            "/**/*.jpeg",
            "/**/*.jpg",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.pdf"
        )
            .addResourceLocations(staticLocations)
            .setCachePeriod(cachePeriod);

        registry.addResourceHandler("/**")
            .addResourceLocations(indexLocations)
            .setCachePeriod(cachePeriod)
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    return location.exists() && location.isReadable() ? location : null;
                }
            });
    }

    @Override
    // Por defecto se mapea todos los atributos a no ser que se indique lo contrario (JsonViews)
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().defaultViewInclusion(true).build();
        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }

}
