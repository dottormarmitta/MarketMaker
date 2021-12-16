package com.guglielmodelsarto.marketMaker.exchange;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.guglielmodelsarto.marketMaker.comparators.OrderingCriterionTradedProductAsk;
import com.guglielmodelsarto.marketMaker.comparators.OrderingCriterionTradedProductBid;
import com.guglielmodelsarto.marketMaker.order.FutureOrderBook;
import com.guglielmodelsarto.marketMaker.order.OrderBook;
import com.guglielmodelsarto.marketMaker.products.TradedProduct;

public class FutureExchange implements Exchange {

	private HashMap<Double, Long> bids = new HashMap<>();
	private List<Double> sortedBids = new ArrayList<>();
	private HashMap<Double, Long> asks = new HashMap<>();
	private List<Double> sortedAsks = new ArrayList<>();
	private static Comparator<TradedProduct> comparatorBid = new OrderingCriterionTradedProductBid();
	private static Comparator<TradedProduct> comparatorAsk = new OrderingCriterionTradedProductAsk();
	private List<TradedProduct> possibleCombos = new ArrayList<>();
	private NavigableSet<TradedProduct> bidPool = new TreeSet<>(comparatorBid);
	private NavigableSet<TradedProduct> askPool = new TreeSet<>(comparatorAsk);
	private TradedProduct cachedProduct;
	OrderBook myBook;

	@Override
	public OrderBook getOrderBook() {

		if(myBook == null) {
			// First I get the Bids
			bidPool.addAll(possibleCombos);
			while(!bidPool.isEmpty()) {
				cachedProduct = bidPool.pollFirst();
				// Reinsert in the tree to check whether the sort has changed
				bidPool.add(cachedProduct);
				cachedProduct = bidPool.pollFirst();
				if(!Double.isNaN(cachedProduct.getTopBid().getPrice())) {
					if(!bids.containsKey(cachedProduct.getTopBid().getPrice())) {
						sortedBids.add(cachedProduct.getTopBid().getPrice());
						bids.put(cachedProduct.getTopBid().getPrice(), 
								cachedProduct.getTopBid().getQuantity());
					} else {
						bids.put(cachedProduct.getTopBid().getPrice(),
								bids.get(cachedProduct.getTopBid().getPrice()) + cachedProduct.getTopBid().getQuantity());
					}
					cachedProduct.executeTopBid();
					if(!cachedProduct.isBidEmpty()) {
						bidPool.add(cachedProduct);
					}
				}
			}
			// Then I do the Asks
			askPool.addAll(possibleCombos);
			while(!askPool.isEmpty()) {
				cachedProduct = askPool.pollFirst();
				// Reinsert in the tree to check whether the sort has changed
				askPool.add(cachedProduct);
				cachedProduct = askPool.pollFirst();
				if(!Double.isNaN(cachedProduct.getTopAsk().getPrice())) {
					if(!asks.containsKey(cachedProduct.getTopAsk().getPrice())) {
						sortedAsks.add(cachedProduct.getTopAsk().getPrice());
						asks.put(cachedProduct.getTopAsk().getPrice(), 
								cachedProduct.getTopAsk().getQuantity());
					} else {
						asks.put(cachedProduct.getTopAsk().getPrice(),
								asks.get(cachedProduct.getTopAsk().getPrice()) + cachedProduct.getTopAsk().getQuantity());
					}
					cachedProduct.executeTopAsk();
					if(!cachedProduct.isAskEmpty()) {
						askPool.add(cachedProduct);
					}
				}
			}

			myBook = new FutureOrderBook(sortedBids, bids, sortedAsks, asks);
		}
		return myBook;

	}

	@Override
	public void addProduct(TradedProduct productToBeAdded) {
		possibleCombos.add(productToBeAdded);
	}

}
