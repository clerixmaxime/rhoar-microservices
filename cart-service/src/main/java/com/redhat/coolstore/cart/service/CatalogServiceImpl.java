package com.redhat.coolstore.cart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.redhat.coolstore.cart.model.Product;

@Component
public class CatalogServiceImpl implements CatalogService {

	@Value("${catalog.service.url}")
	private String catalogServiceUrl;
	
	@Override
	public Product getProduct(String itemId) {
		
		Product p = null;
		RestTemplate _resttpl = new RestTemplate();
		String _url = catalogServiceUrl + "/product/" + itemId;
		
		try {
			
			ResponseEntity<Product> _re_product = _resttpl.getForEntity(_url, Product.class);
			p = _re_product.getBody();
			
		} catch(HttpClientErrorException error) {
			if (error.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw error;
			}
		}
		
		return p;
	}

}
