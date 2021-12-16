package com.guglielmodelsarto.marketMaker.order;

/**
 * Interface representing a book order.
 * It can either be bid or ask.
 * 
 * @author Guglielmo Del Sarto
 */
public interface Order {
	
	public ExecutableOrder getExecutable();
	
	public void executeOrder(ExecutableOrder orderToBeExecuted);

}
