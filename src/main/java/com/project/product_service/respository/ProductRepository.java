package com.project.product_service.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product,String>{
}
