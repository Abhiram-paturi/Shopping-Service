package com.project.product_service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.project.product_service.DTO.ProductRequest;
import com.project.product_service.respository.ProductRepository;

import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    
	@Container
	static MongoDBContainer mongoDBContainer =new MongoDBContainer("mongo:4.4.2");
    
	@Autowired
	private MockMvc mockMvc;
    
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

    @DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
		dymDynamicPropertyRegistry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	//Integeration test

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

	  mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
	         .contentType(MediaType.APPLICATION_JSON)
			 .content(productRequestString))
			 .andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
		                     .name("Iphone 13")
							 .description("mobile")
							 .price(BigDecimal.valueOf(1200))
							  .build();
		}

}
