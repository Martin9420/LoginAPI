package com.ibm.jaxrs.sample;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest/*")
public class HelloWorldAppConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(com.ibm.jaxrs.sample.HelloWorldResource01.class);
    classes.add(com.ibm.jaxrs.sample.HelloWorldResource02.class);
    return classes;
  }
}