package com.scala.test.rest

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

abstract class ServerRest {

//  val PORT = 7077
//  var server: Server = _
//
//  def start {
//    val config: ResourceConfig = new ResourceConfig()
//    config.register(this)
//    val servlet = new ServletHolder(new ServletContainer(config))
//
//    server = new Server(PORT)
//    val context = new ServletContextHandler(server, "/")
//    context.addServlet(servlet, "/*")
//    server.start()
//  }
//
//  def stop {
//    if (server != null) {
//      server.stop()
//    }
//  }
}
