package com.guglielmodelsarto.marketMaker.order;

/**
 * Container interface keeping in memory
 * <br>
 * - quantity
 * <br>
 * - price
 * <br>
 * that can be executed for an order.
 * 
 * @author Guglielmo Del Sarto
 */
public interface ExecutableOrder {

	/**
	 * Give the price of the order
	 * 
	 * @return price of the order as double
	 */
	public double getPrice();

	/**
	 * Give the available quantity for the specified price
	 * 
	 * @return quantity of the order as long
	 */
	public long getQuantity();

	public void composeLong(ExecutableOrder longOrder);

	public void composeShort(ExecutableOrder shortOrder);

}
