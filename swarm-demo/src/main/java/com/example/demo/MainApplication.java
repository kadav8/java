package com.example.demo;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import com.example.demo.rest.HelloWorldEndpoint;

public class MainApplication {
	
	public static void main(String...args) throws Exception {
        Swarm swarm = new Swarm();
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addClass(HelloWorldEndpoint.class);
        swarm.start();
        swarm.deploy(deployment);
    }
}
