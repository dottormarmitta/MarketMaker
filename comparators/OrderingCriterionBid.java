package com.guglielmodelsarto.marketMaker.comparators;

import java.util.Comparator;

import com.guglielmodelsarto.marketMaker.order.Order;

public class OrderingCriterionBid implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		if (Double.isNaN(o1.getExecutable().getPrice())) {
			return 0;
		}
		return o1.getExecutable().getPrice() < o2.getExecutable().getPrice() ?
				1 : -1;
	}

}
