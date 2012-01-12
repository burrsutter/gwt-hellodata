package org.jboss.as.quickstarts.gwthelloworld.client.local;

// import org.jboss.as.quickstarts.gwthelloworld.server.HelloWorldResource;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents the JSON response object sent back from the {@link ContactResource
 * JAX-RS ContactResource service}.
 * 
 * @author Burr Sutter <bsutter@redhat.com>
 */
public class ContactResponse extends JavaScriptObject {

  protected ContactResponse() {}
  
  public final native String getName() /*-{ return this.name; }-*/;
  public final native Long getId() /*-{ return this.id; }-*/;
  public final native String getEmail() /*-{ return this.email; }-*/;
  public final native String getPhoneNumber() /*-{ return this.phoneNumber; }-*/;

}
