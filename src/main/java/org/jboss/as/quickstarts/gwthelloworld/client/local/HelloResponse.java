package org.jboss.as.quickstarts.gwthelloworld.client.local;

import org.jboss.as.quickstarts.gwthelloworld.server.HelloWorldResource;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents the JSON response object sent back from the {@link HelloWorldResource
 * JAX-RS HelloWorldResource service}.
 * 
 * @author Jonathan Fuerth <jfuerth@redhat.com>
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class HelloResponse extends JavaScriptObject {

  protected HelloResponse() {}
  
  public final native String getResult() /*-{ return this.result; }-*/;

}
