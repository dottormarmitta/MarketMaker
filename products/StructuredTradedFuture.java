package com.guglielmodelsarto.marketMaker.products;

import java.util.ArrayList;
import java.util.List;

import com.guglielmodelsarto.marketMaker.order.ExecutableContainer;
import com.guglielmodelsarto.marketMaker.order.ExecutableOrder;

public class StructuredTradedFuture implements TradedProduct {
	
	private static ExecutableOrder ordernotAvailable = new ExecutableContainer(Double.NaN, 0);
	private List<TradedProduct> longPositions  = new ArrayList<>();
	private List<TradedProduct> shortPositions = new ArrayList<>();
	ExecutableOrder toBeEx;

	public StructuredTradedFuture(List<TradedProduct> longPositions, List<TradedProduct> shortPositions) {
		this.longPositions = longPositions;
		this.shortPositions = shortPositions;
	}

	public StructuredTradedFuture(List<TradedProduct> orders, boolean isLong) {
		if(isLong) {
			this.longPositions = orders;
		} else {
			this.shortPositions = orders;
		}
	}

	@Override
	public ExecutableOrder getTopBid() {
		ExecutableOrder outpoutOrder = new ExecutableContainer(0.0, Long.MAX_VALUE);
		for(TradedProduct l : longPositions) {
			if(l.isBidEmpty()) {
				return ordernotAvailable;
			}
			outpoutOrder.composeLong(l.getTopBid());
		}
		for(TradedProduct s : shortPositions) {
			if(s.isAskEmpty()) {
				return ordernotAvailable;
			}
			outpoutOrder.composeShort(s.getTopAsk());
		}
		return outpoutOrder;
	}

	@Override
	public ExecutableOrder getTopAsk() {
		ExecutableOrder outpoutOrder = new ExecutableContainer(0.0, Long.MAX_VALUE);
		for(TradedProduct l : longPositions) {
			if(l.isAskEmpty()) {
				return ordernotAvailable;
			}
			outpoutOrder.composeLong(l.getTopAsk());
		}
		for(TradedProduct s : shortPositions) {
			if(s.isBidEmpty()) {
				return ordernotAvailable;
			}
			outpoutOrder.composeShort(s.getTopBid());
		}
		return outpoutOrder;
	}

	@Override
	public void executeTopBid() {
		toBeEx = this.getTopBid();
		for(TradedProduct l : longPositions) {
			l.executeOrder(toBeEx, true);
		}
		for(TradedProduct s : shortPositions) {
			s.executeOrder(toBeEx, false);
		}
	}

	@Override
	public void executeTopAsk() {
		toBeEx = this.getTopAsk();
		for(TradedProduct l : longPositions) {
			l.executeOrder(toBeEx, false);
		}
		for(TradedProduct s : shortPositions) {
			s.executeOrder(toBeEx, true);
		}
	}
	
	@Override
	public boolean isBidEmpty() {
		for(TradedProduct l : longPositions) {
			if(l.isBidEmpty()) {
				return true;
			}
		}
		for(TradedProduct s : shortPositions) {
			if(s.isAskEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAskEmpty() {
		for(TradedProduct l : longPositions) {
			if(l.isAskEmpty()) {
				return true;
			}
		}
		for(TradedProduct s : shortPositions) {
			if(s.isBidEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void executeOrder(ExecutableOrder order, boolean isBid) {
		// TODO Auto-generated method stub
		
	}	

}
