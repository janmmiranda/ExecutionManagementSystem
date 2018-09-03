package com.trading.test;

import java.util.List;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.trading.models.EquityResponse;
import com.trading.models.Order;
import com.trading.repo.EMSRepo;
import com.trading.services.EMSService;
import java.sql.Connection;
public class TestEMS {
	
	public static void main(String[] args) {
				
		EMSRepo emsRepo = new EMSRepo();
			
		Order or = emsRepo.retrieveOrder("1a4");
		System.out.println(or);
		
		
		or.setAllocatedQuantity(500);
		or.setOpenQuantity(500);
		emsRepo.orderUpdate(or);
		
		or = emsRepo.retrieveOrder("1a4");
		System.out.println(or);
		
	}

}
