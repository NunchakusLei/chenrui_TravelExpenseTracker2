package ca.ualberta.cs.chenrui_travelexpensetracker;

public class Currency {
	private String type;
	private double amount;
	

	public Currency(String type,double d) {
		this.setType(type);
		this.setAmount(d);
	}
	
	public Currency(String type){
		this.setType(type);
		this.setAmount(0);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString(){
		return this.type + this.amount;
	}
	
	public boolean isSameType(Currency currency){
		return this.type.equals(currency.getType());	
	}
}
