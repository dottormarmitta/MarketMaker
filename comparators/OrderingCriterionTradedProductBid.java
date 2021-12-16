package com.guglielmodelsarto.marketMaker.comparators;

import java.util.Comparator;

import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public class OrderingCriterionTradedProductBid implements Comparator<TradedProduct> {
	
	@Override
	public int compare(TradedProduct o1, TradedProduct o2) {
		if (Double.isNaN(o1.getTopBid().getPrice())) {
			return 0;
		}
		return o1.getTopBid().getPrice() < o2.getTopBid().getPrice() ?
				1 : -1;
	}

}
