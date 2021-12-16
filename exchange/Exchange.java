package com.guglielmodelsarto.marketMaker.exchange;

import com.guglielmodelsarto.marketMaker.order.OrderBook;
import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public interface Exchange {
	
	public OrderBook getOrderBook();
	
	public void addProduct(TradedProduct productToBeAdded);

}
