package com.guglielmodelsarto.marketMaker.order;

public class ExecutableContainer implements ExecutableOrder {
	
	private double price;
	private long quantity;
	
	public ExecutableContainer(double price, long quantity) {
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public double getPrice() {
		if(quantity <= 0) {
			return Double.NaN;
		}
		return price;
	}

	@Override
	public long getQuantity() {
		return quantity;
	}

	@Override
	public void composeLong(ExecutableOrder longOrder) {
		price += longOrder.getPrice();
		quantity = Math.min(quantity, longOrder.getQuantity());
	}

	@Override
	public void composeShort(ExecutableOrder shortOrder) {
		price -= shortOrder.getPrice();
		quantity = Math.min(quantity, shortOrder.getQuantity());
	}

}
