package com.guglielmodelsarto.marketMaker.exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.guglielmodelsarto.marketMaker.calendar.Date;
import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public class FutureExchangeBuilder implements ExchangeBuilder {
	
	private HashMap<Date, List<Date>> graphOfConnections = new HashMap<Date, List<Date>>();
	private HashMap<String, TradedProduct> availableProducts  = new HashMap<String, TradedProduct>();
	private Date currentNode;
	private List<TradedProduct> longOrders;
	private List<TradedProduct> shortOrders;
	private List<Date> nodeToVisit;
	Exchange outputExchange = new FutureExchange();
	
	@Override
	public void addFuture(TradedProduct future, Date beginDate, Date endDate) {
		availableProducts.putIfAbsent(beginDate.name() + endDate.name(), future);
		graphOfConnections.putIfAbsent(beginDate, new ArrayList<>());
		graphOfConnections.get(beginDate).add(endDate);
		graphOfConnections.putIfAbsent(endDate, new ArrayList<>());
		graphOfConnections.get(endDate).add(beginDate);
		/* TODO: remove debugging annotation
		System.out.println(graphOfConnections.toString());
		System.out.println(availableProducts.toString());
		System.out.println(beginDate.compareTo(endDate));
		*/
	}

	@Override
	public Exchange getExchange(Date beginDate, Date endDate) {
		
		
		return outputExchange;
	}

}
