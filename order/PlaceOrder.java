package com.guglielmodelsarto.marketMaker.order;

public class PlaceOrder implements Order {
	
	private double price;
	private long quantity;
	
	public PlaceOrder(long quantity, double price) {
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public ExecutableOrder getExecutable() {
		return new ExecutableContainer(price, quantity);
	}

	@Override
	public void executeOrder(ExecutableOrder orderToBeExecuted) {
		quantity -= orderToBeExecuted.getQuantity();
	}

}
