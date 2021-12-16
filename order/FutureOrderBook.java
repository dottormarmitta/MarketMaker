package com.guglielmodelsarto.marketMaker.order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FutureOrderBook implements OrderBook {
	
	private HashMap<Double, Long> bids = new HashMap<>();
	private List<Double> sortedBids;
	private HashMap<Double, Long> asks = new HashMap<>();
	private List<Double> sortedAsks;
	DecimalFormat df = new DecimalFormat("0.00");
	

	public FutureOrderBook(List<Double> sortedBids, HashMap<Double, Long> bids,
			List<Double> sortedAsks, HashMap<Double, Long> asks) {
		this.bids = bids;
		this.sortedBids = sortedBids;
		this.asks = asks;
		this.sortedAsks = sortedAsks;
	}

	@Override
	public void flushInCsv(String location) throws IOException {
		
		
		List<Double> bidsA = new ArrayList<>();
		List<Double> asksA = new ArrayList<>();
		for(Double s : sortedBids) {
			for(int i = 0; i < bids.get(s); i++) {
				bidsA.add(s);
			}
		}
		for(Double s : sortedAsks) {
			for(int i = 0; i < asks.get(s); i++) {
				asksA.add(s);
			}
		}
		File file = new File(location);
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));
		outputWriter.write("BID" + "," + "ASK");
		outputWriter.newLine();
		for(int i = 0; i < Math.max(bidsA.size(), asksA.size()); i++) {
			if(i < bidsA.size() && i < asksA.size()) {
				outputWriter.write(Double.toString(bidsA.get(i)) + "," +
						Double.toString(asksA.get(i)));
				outputWriter.newLine();
			} else if(i < bidsA.size() && i >= asksA.size()) {
				outputWriter.write(Double.toString(bidsA.get(i)) + "," +
						" ");
				outputWriter.newLine();
			} else if(i >= bidsA.size() && i < asksA.size()) {
				outputWriter.write(" " + "," +
						Double.toString(asksA.get(i)));
				outputWriter.newLine();
			}
		}
		outputWriter.flush();  
		outputWriter.close();

	}

	@Override
	public HashMap<Double, Long> getBidAsMap() {
		return bids;
	}

	@Override
	public HashMap<Double, Long> getAskAsMap() {
		return asks;
	}

	@Override
	public void printBid() {
		System.out.println("     BID");
		System.out.println("PRICE" + "   " + "QUANTITY");
		for(Double k : sortedBids) {
			System.out.println(df.format(k) + "  -  " + bids.get(k));
		}
	}

	@Override
	public void printAsk() {
		System.out.println("     ASK");
		System.out.println("PRICE" + "   " + "QUANTITY");
		for(Double k : sortedAsks) {
			System.out.println(df.format(k) + "  -  " + asks.get(k));
		}
	}

}
