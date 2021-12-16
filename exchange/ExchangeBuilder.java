package com.guglielmodelsarto.marketMaker.exchange;

import com.guglielmodelsarto.marketMaker.calendar.Date;
import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public interface ExchangeBuilder {
	
	public void addFuture(TradedProduct future, Date beginDate, Date endDate);
	
	public Exchange getExchange(Date beginDate, Date endDate);

}
