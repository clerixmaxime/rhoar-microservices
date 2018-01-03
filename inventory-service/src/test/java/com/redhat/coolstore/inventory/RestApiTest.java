package com.redhat.coolstore.inventory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.redhat.coolstore.inventory.model.Inventory;

@RunWith(Arquillian.class)
public class RestApiTest {

	@CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm().withProfile("local");
    }
	
	@Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestApplication.class.getPackage())
                .addAsResource("project-local.yml", "project-local.yml")
                .addAsResource("META-INF/test-persistence.xml",  "META-INF/persistence.xml")
                .addAsResource("META-INF/test-load.sql",  "META-INF/test-load.sql");
    }
	
	@Test
	@RunAsClient
    public void test_rest_api() {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/inventory").path("/123456");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        JsonObject value = Json.parse(response.readEntity(String.class)).asObject();
        
        value.
        System.out.println("My value" + value);
        Assert.assertNotNull(value);
    }
	
	@Test
	@RunAsClient
    public void test_rest_healthcheck_api() {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/health");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        JsonObject value = Json.parse(response.readEntity(String.class)).asObject();
        System.out.println("My value" + value);
        Assert.assertNotNull(value);
    }
}
