package com.java.test.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public abstract class ServerRest {

    private static final int PORT = 7070;
    private Server server;

    public void start() throws Exception {
        ResourceConfig config = new ResourceConfig();
        config.register(this);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");
        server.start();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }
}
