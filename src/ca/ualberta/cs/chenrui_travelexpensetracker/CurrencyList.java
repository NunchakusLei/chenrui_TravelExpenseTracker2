package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.util.ArrayList;

public class CurrencyList {
	private ArrayList<Currency> currencyList;
	
	public ArrayList<Currency> getCurrencyList(){
		return this.currencyList;
	}
	
	public CurrencyList() {
		this.currencyList = new ArrayList<Currency>();
	}

	public int size(){
		return this.currencyList.size();
	}
	
	public void add(Currency object){
		this.currencyList.add(object);
	}
	
	public void add(int i,Currency object){
		this.currencyList.add(i,object);
	}
	
	public void remove(int i){
		this.currencyList.remove(i);
	}
	
	public void remove(Currency object){
		this.currencyList.remove(object);
	}
	
	
	
	
}
