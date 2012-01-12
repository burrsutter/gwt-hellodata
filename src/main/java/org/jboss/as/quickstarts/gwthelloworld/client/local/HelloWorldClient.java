package org.jboss.as.quickstarts.gwthelloworld.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

import java.util.logging.Logger;


/**
 * Companion class to the UI declared in {@linkplain HelloWorldClient.ui.xml}.
 * Handles events and updates the DOM in response.
 * 
 * @author Jonathan Fuerth <jfuerth@redhat.com>
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class HelloWorldClient {

	interface MyUiBinder extends UiBinder<Panel, HelloWorldClient> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	Panel root;
	@UiField
	Label result;
	@UiField
	Label listOfContacts;
	@UiField
	InputElement name;
	@UiField
	Button sayHelloButton;
	@UiField
	Button getContacts;

	private static Logger log = Logger.getLogger("");

	HelloWorldClient() {
		uiBinder.createAndBindUi(this);
		// what does this line do?
		DOM.setElementAttribute(sayHelloButton.getElement(), "name",
				"sayHelloButton");
	}

	/**
	 * Handles a click of the button by sending an AJAX request to the
	 * HelloWorldResource and then updating the {@code result} label in
	 * response.
	 * 
	 * @param e
	 *            Details of the click event. Ignored by this handler.
	 */
	@UiHandler("sayHelloButton")
	public void onButtonClick(ClickEvent e) {
		try {
			new RequestBuilder(RequestBuilder.GET, "rest/hello/json/"
					+ URL.encodePathSegment(name.getValue())).sendRequest(null,
					new RequestCallback() {

						@Override
						public void onResponseReceived(Request request,
								Response response) {
							if (response.getStatusCode() == Response.SC_OK) {
								HelloResponse r = (HelloResponse) JsonUtils
										.safeEval(response.getText());
								result.setText(r.getResult());
							} else {
								handleError("Server responded with status code "
										+ response.getStatusCode());
							}
						}

						@Override
						public void onError(Request request, Throwable exception) {
							handleError(exception.getMessage());
						}
					});
		} catch (RequestException exception) {
			handleError(exception.getMessage());
		}
	}

	@UiHandler("getContacts")
	public void onButtonClickGetContacts(ClickEvent e) {
		log.info("onButtonClickGetContacts");
		try {
			new RequestBuilder(RequestBuilder.GET, "rest/contacts")
					.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request,
								Response response) {
							if (response.getStatusCode() == Response.SC_OK) {
								log.info("Response: " + response.getText());
								JsArray<ContactResponse> contacts = asArrayOfContacts(response.getText());
								for (int i = 0; i < contacts.length(); i++) {
									ContactResponse c = contacts.get(i);
									log.info(c.getName() + " " + c.getEmail());
								}
							} else {
								log.info("Error? " + response.getStatusText());
								handleErrorContacts("Server responded with status code "
										+ response.getStatusCode());
							}
						}

						@Override
						public void onError(Request request, Throwable exception) {
							handleErrorContacts(exception.getMessage());
						}
					});
		} catch (RequestException exception) {
			handleErrorContacts(exception.getMessage());
		}

		// listOfContacts.setText("Now Again: " + new java.util.Date());
	}

	private void handleError(String details) {
		result.setText("Sorry, can't say hello now. " + details);
	}

	private void handleErrorContacts(String details) {
		listOfContacts.setText("Error: " + details);
	}

	/**
	 * Returns the DOM fragment that this
	 * 
	 * @return
	 */
	public Panel getElement() {
		return root;
	}

	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JsArray<ContactResponse> asArrayOfContacts(String json) /*-{
		return eval(json);
	}-*/;
}
