package com.mycompany;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class PropertyProducer {

    private Properties properties;

    @PostConstruct
    private void initProperties() {
        properties = properties = new Properties();

        String path = System.getProperty("property.file.location");
        String propfilename = System.getProperty("property.file.name");
        propfilename = (propfilename != null && !propfilename.isEmpty()) ? propfilename : "application.properties";

        InputStream is = null;
        try {
            File file = null;
            if (path != null && !path.isEmpty()) {
                file = new File(path + propfilename);
            }
            if (file != null && file.exists() && file.canRead()) {
                is = new FileInputStream(file);
            } else {
                is = this.getClass().getClassLoader().getResourceAsStream(propfilename);
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Could not init configuration", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    @Produces
    @Config
    public String exposeConfig(InjectionPoint injectionPoint) {
        Config config = injectionPoint.getAnnotated().getAnnotation(Config.class);
        return properties.getProperty(config.value());
    }
}
