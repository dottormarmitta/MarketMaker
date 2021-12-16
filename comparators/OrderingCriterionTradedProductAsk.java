package com.guglielmodelsarto.marketMaker.comparators;

import java.util.Comparator;

import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public class OrderingCriterionTradedProductAsk implements Comparator<TradedProduct> {
	
	@Override
	public int compare(TradedProduct o1, TradedProduct o2) {
		if (Double.isNaN(o1.getTopAsk().getPrice())) {
			return 0;
		}
		return o1.getTopAsk().getPrice() < o2.getTopAsk().getPrice() ?
				-1 : 1;
	}

}
