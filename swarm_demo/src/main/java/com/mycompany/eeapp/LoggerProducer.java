package com.mycompany.eeapp;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.Produces;
import java.util.logging.Logger;

public class LoggerProducer {

    @Produces
    public Logger exposeLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
