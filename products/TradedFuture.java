package com.guglielmodelsarto.marketMaker.products;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.guglielmodelsarto.marketMaker.comparators.OrderingCriterionBid;
import com.guglielmodelsarto.marketMaker.order.ExecutableContainer;
import com.guglielmodelsarto.marketMaker.order.ExecutableOrder;
import com.guglielmodelsarto.marketMaker.order.Order;

public class TradedFuture implements TradedProduct {

	private static final Comparator<Order> ORDERING = new OrderingCriterionBid();
	private static ExecutableOrder ordernotAvailable = new ExecutableContainer(Double.NaN, 0);
	private static long IDgenerator = 0;
	private NavigableSet<Order> bidPool = new TreeSet<Order>(ORDERING);
	private NavigableSet<Order> askPool = new TreeSet<Order>(ORDERING.reversed());
	private Order cachedOrder;
	private long currentID;

	public TradedFuture(List<Order> bids, List<Order> asks) {
		bidPool.addAll(bids);
		askPool.addAll(asks);
		currentID = IDgenerator++;
	}

	@Override
	public ExecutableOrder getTopBid() {
		if(bidPool.isEmpty()) {
			return ordernotAvailable;
		}
		return bidPool.first().getExecutable();
	}

	@Override
	public ExecutableOrder getTopAsk() {
		if(askPool.isEmpty()) {
			return ordernotAvailable;
		}
		return askPool.first().getExecutable();
	}

	@Override
	public void executeTopBid() {
		cachedOrder = bidPool.pollFirst();
		cachedOrder.executeOrder(cachedOrder.getExecutable());
		if(!Double.isNaN(cachedOrder.getExecutable().getPrice())) {
			bidPool.add(cachedOrder);
		}
	}

	@Override
	public void executeTopAsk() {
		cachedOrder = askPool.pollFirst();
		cachedOrder.executeOrder(cachedOrder.getExecutable());
		if(!Double.isNaN(cachedOrder.getExecutable().getPrice())) {
			askPool.add(cachedOrder);
		}
	}

	@Override
	public void executeOrder(ExecutableOrder order, boolean isBid) {
		if(isBid && !bidPool.isEmpty()) {
			cachedOrder = bidPool.pollFirst();
			cachedOrder.executeOrder(order);
			if(!Double.isNaN(cachedOrder.getExecutable().getPrice())) {
				bidPool.add(cachedOrder);
			}
		} else if (!askPool.isEmpty()) {
			cachedOrder = askPool.pollFirst();
			cachedOrder.executeOrder(order);
			if(!Double.isNaN(cachedOrder.getExecutable().getPrice())) {
				askPool.add(cachedOrder);
			}
		}
	}
	
	@Override
	public boolean isBidEmpty() {
		return bidPool.isEmpty();
	}

	@Override
	public boolean isAskEmpty() {
		return askPool.isEmpty();
	}

	@Override
	public long getId() {
		return currentID;
	}

}
