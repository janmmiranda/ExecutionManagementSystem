package com.trading.test;

import java.util.List;

import com.trading.models.Order;
import com.trading.repo.EMSRepo;
import com.trading.services.EMSService;

public class TestEMS {
	
	public static void main(String[] args) {
		
		EMSRepo emsRepo = new EMSRepo();
		EMSService emsService = new EMSService();
		
		List<Order> blockOrders = emsRepo.retrieveBlock("B01");
		System.out.println("From Repo");
		for(Order o : blockOrders) {
			System.out.println(o);
		}
		
		blockOrders = emsService.forwardOrder(blockOrders);
		System.out.println("From Broker");
		for(Order o : blockOrders) {
			System.out.println(o);
		}
		
	}

}
