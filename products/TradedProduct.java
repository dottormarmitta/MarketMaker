package com.guglielmodelsarto.marketMaker.products;

import com.guglielmodelsarto.marketMaker.order.ExecutableOrder;

public interface TradedProduct {
	
	public ExecutableOrder getTopBid();
	
	public ExecutableOrder getTopAsk();
	
	public void executeTopBid();
	
	public void executeTopAsk();
	
	public void executeOrder(ExecutableOrder order, boolean isBid);
	
	public boolean isBidEmpty();
	
	public boolean isAskEmpty();
	
	public long getId();

}
