package com.redhat.coolstore.inventory.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.wildfly.swarm.health.Health;
import org.wildfly.swarm.health.HealthStatus;

@Path("/")
public class HealthCheckResource {

	@Health
	@GET
	@Path("/health")
	public HealthStatus check() {
		return HealthStatus.named("server-state").up();
	}
}
