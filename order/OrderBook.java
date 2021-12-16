package com.guglielmodelsarto.marketMaker.order;

import java.io.IOException;
import java.util.HashMap;

public interface OrderBook {
	
	public void flushInCsv(String location) throws IOException;
	
	public HashMap<Double, Long> getBidAsMap();
	
	public HashMap<Double, Long> getAskAsMap();
	
	public void printBid();
	
	public void printAsk();

}
