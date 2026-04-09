package com.example.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

     @Bean
	 public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
           Inventory inventory1 = new Inventory();
		   inventory1.setSkuCode("iphone_13");
		   inventory1.setQuantity(200);
           
		   Inventory inventory2 = new Inventory();
		   inventory2.setSkuCode("iphone_13_pro");
		   inventory2.setQuantity(0);

		   inventoryRepository.save(inventory1);
		   inventoryRepository.save(inventory2);
		};
	 }
}
