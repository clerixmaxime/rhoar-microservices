package com.redhat.coolstore.catalog.verticle;

import com.redhat.coolstore.catalog.api.ApiVerticle;
import com.redhat.coolstore.catalog.verticle.service.CatalogService;
import com.redhat.coolstore.catalog.verticle.service.CatalogServiceVertxEBProxy;
import com.redhat.coolstore.catalog.verticle.service.CatalogVerticle;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {

		// ----
		// To be implemented
		//
		// * Create a `ConfigStoreOptions` instance.
		// * Set the type to "configmap" and the format to "yaml".
		// * Configure the `ConfigStoreOptions` instance with the name and the key of
		// the configmap
		// * Create a `ConfigRetrieverOptions` instance
		// * Add the `ConfigStoreOptions` instance as store to the
		// `ConfigRetrieverOptions` instance
		// * Create a `ConfigRetriever` instance with the `ConfigRetrieverOptions`
		// instance
		// * Use the `ConfigRetriever` instance to retrieve the configuration
		// * If the retrieval was successful, call the `deployVerticles` method,
		// otherwise fail the `startFuture` object.
		//
		// ----
		
		ConfigStoreOptions appStore = new ConfigStoreOptions();
		
		appStore.setType("configmap");
		appStore.setFormat("yaml");
		appStore.setConfig(new JsonObject()
                .put("name", "app-config")
                .put("key", "app-config.yaml"));
		
		ConfigRetrieverOptions appRetriever = new ConfigRetrieverOptions();
		appRetriever.addStore(appStore);
		
		ConfigRetriever configRetriever = ConfigRetriever.create(vertx, appRetriever);
		
		configRetriever.getConfig(res -> {
			if (res.succeeded()) {
				deployVerticles(res.result(), startFuture);
			} else {
				startFuture.fail(res.cause());
			}
		});
	}

	private void deployVerticles(JsonObject config, Future<Void> startFuture) {

		// ----
		// To be implemented
		//
		// * Create a proxy for the `CatalogService`.
		// * Create an instance of `ApiVerticle` and `CatalogVerticle`
		// * Deploy the verticles
		// * Make sure to pass the verticle configuration object as part of the
		// deployment options
		// * Use `Future` objects to get notified of successful deployment (or failure)
		// of the verticle deployments.
		// * Use a `CompositeFuture` to coordinate the deployment of both verticles.
		// * Complete or fail the `startFuture` depending on the result of the
		// CompositeFuture
		//
		// ----

		CatalogService catalogServiceProxy = CatalogService.createProxy(vertx);

		DeploymentOptions options = new DeploymentOptions();
		options.setConfig(config);
		
		Future<String> apiVerticleFuture = Future.future();
		Future<String> catalogVerticleFuture = Future.future();
				
		vertx.deployVerticle(new CatalogVerticle(), options, catalogVerticleFuture.completer());
		
		vertx.deployVerticle(new ApiVerticle(catalogServiceProxy), options, apiVerticleFuture.completer());
		
		CompositeFuture.all(apiVerticleFuture, catalogVerticleFuture).setHandler(ar -> {
		    if (ar.succeeded()) {
		    		startFuture.complete();
		    } else {
		    		startFuture.fail("");
		    }
		});
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
	}

}
