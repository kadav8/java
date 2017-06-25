package com.mycompany.eeapp;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class PropertyProducer {

    private Properties properties = new Properties();

    @PostConstruct
    private void initProperties() {
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
                    // ignored
                }
            }
        }
    }

    @Produces
    @Config
    public String exposeConfig(InjectionPoint injectionPoint) {
        Config config = injectionPoint.getAnnotated().getAnnotation(Config.class);
        String value = properties.getProperty(config.key());
        if(value == null && config.defaultValue() != null && !config.defaultValue().isEmpty()) {
            value = config.defaultValue();
        }
        return value;
    }
}
