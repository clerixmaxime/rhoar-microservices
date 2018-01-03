package com.redhat.coolstore.cart.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redhat.coolstore.cart.model.ShoppingCart;
import com.redhat.coolstore.cart.service.ShoppingCartService;

@Component
@Path("/cart")
public class CartEndpoint {

	@Autowired
	private ShoppingCartService _scs;
	
	private static final Logger LOG = LoggerFactory.getLogger(CartEndpoint.class);

	@GET
	@Path("/{cartId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart getCart(@PathParam("cartId") String cartId) {

		return _scs.getShoppingCart(cartId);

	}

	@POST
	@Path("/{cartId}/{itemId}/{quantity}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart addCart(@PathParam("cartId") String cartId, @PathParam("itemId") String itemId,
			@PathParam("quantity") int quantity) {

		return _scs.addToCart(cartId, itemId, quantity);

	}

	@DELETE
	@Path("/{cartId}/{itemId}/{quantity}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart deleteCart(@PathParam("cartId") String cartId, @PathParam("itemId") String itemId,
			@PathParam("quantity") int quantity) {

		return _scs.removeFromCart(cartId, itemId, quantity);

	}
	
	@POST
	@Path("/checkout/{cartId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart addCart(@PathParam("cartId") String cartId) {

		LOG.info("Cart has been checked out");
		
		return _scs.checkoutShoppingCart(cartId);

	}

}
