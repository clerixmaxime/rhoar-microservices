package com.redhat.coolstore.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="PRODUCT_INVENTORY")
@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String itemid;
	private String location;
	private int quantity;
	private String link;
	
	public Inventory() {
		super();
	}
	
	public Inventory(String itemid, String location, int quantity, String link) {
		super();
		this.itemid = itemid;
		this.location = location;
		this.quantity = quantity;
		this.link = link;
	}
	
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
