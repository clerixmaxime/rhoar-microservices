package com.redhat.coolstore.inventory.service;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import com.redhat.coolstore.inventory.model.Inventory;

@RunWith(Arquillian.class)
public class InventoryServiceTest {

	@Inject
	InventoryService inventory;
	
    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm().withProfile("local");
    }
    
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, InventoryService.class.getPackage())
                .addPackages(true, Inventory.class.getPackage())
                .addAsResource("project-local.yml", "project-local.yml")
                .addAsResource("META-INF/test-persistence.xml",  "META-INF/persistence.xml")
                .addAsResource("META-INF/test-load.sql",  "META-INF/test-load.sql");
    }
    
    @Test
    public void should_get_inventory_with_itemid() {
    		Inventory i = inventory.getInventory("123456");
        Assert.assertEquals(null, i.getLocation(), "location");
    }
    
    @Test
    public void should_fail_inventory_with_itemid() {
    		Inventory i = inventory.getInventory("111");
        Assert.assertNull(i);
    }
}
