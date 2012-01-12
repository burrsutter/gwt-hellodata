package org.jboss.as.quickstarts.gwthelloworld.server;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


import java.util.logging.Logger;

import java.util.List;

@Path("/contacts")
public class ContactResource {
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;


	@GET
	@Produces("application/json")
	public List<Contact> listAllContacts() {
		log.info("listAllContacts");
		final List<Contact> results = em.createQuery("select c from Contact c order by c.name").getResultList();
		log.info("listAllContacts results.size: " + results.size());
		return results;
	}
}
