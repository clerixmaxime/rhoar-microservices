package com.redhat.coolstore.catalog.verticle.service;

import java.util.List;
import java.util.stream.Collectors;

import com.redhat.coolstore.catalog.model.Product;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class CatalogServiceImpl implements CatalogService {

	private MongoClient client;

	public CatalogServiceImpl(Vertx vertx, JsonObject config, MongoClient client) {
		this.client = client;
	}

	@Override
	public void getProducts(Handler<AsyncResult<List<Product>>> resulthandler) {
		// ----
		// To be implemented
		//
		// Use the `MongoClient.find()` method.
		// Use an empty JSONObject for the query
		// The collection to search is "products"
		// In the handler implementation, transform the `List<JSONObject>` to
		// `List<Person>` - use Java8 Streams!
		// Use a Future to set the result on the handle() method of the result handler
		// Don't forget to handle failures!
		// ----
		client.find("products", new JsonObject(), ar -> {
			if (ar.succeeded()) {
				List<Product> products = ar.result().stream().map(p -> p.mapTo(Product.class))
						.collect(Collectors.toList());
				resulthandler.handle(Future.succeededFuture(products));

			} else {
				resulthandler.handle(Future.failedFuture("Can't retrieve products"));
			}
		});
	}

	@Override
	public void getProduct(String itemId, Handler<AsyncResult<Product>> resulthandler) {
		// ----
		// To be implemented
		//
		// Use the `MongoClient.find()` method.
		// Use a JSONObject for the query with the field 'itemId' set to the product
		// itemId
		// The collection to search is "products"
		// In the handler implementation, transform the `List<JSONObject>` to `Person` -
		// use Java8 Streams!
		// If the product is not found, the result should be set to null
		// Use a Future to set the result on the handle() method of the result handler
		// Don't forget to handle failures!
		// ----

		JsonObject product = new JsonObject();
		product.put("itemId", itemId);

		client.find("products", product, ar -> {
			if (ar.succeeded()) {
				resulthandler.handle(Future.succeededFuture(new Product(ar.result().get(0))));
			} else {
				resulthandler.handle(Future.failedFuture("Can't retrieve product with id: " + itemId));
			}
		});
	}

	@Override
	public void addProduct(Product product, Handler<AsyncResult<String>> resulthandler) {
		client.save("products", toDocument(product), resulthandler);
	}

	@Override
	public void ping(Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture("OK"));
	}

	private JsonObject toDocument(Product product) {
		JsonObject document = product.toJson();
		document.put("_id", product.getItemId());
		return document;
	}
}
