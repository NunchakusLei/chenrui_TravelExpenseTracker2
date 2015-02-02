package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private ArrayList<Currency> totalCurrencyList;
	private ArrayList<Expense> expenseList;
	private String status;
	
	public Claim() {
		this.totalCurrencyList = new ArrayList<Currency>();
		this.expenseList = new ArrayList<Expense>();
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String TotalCurrencyListToString(){
		String out = "";
		if (totalCurrencyList.size() != 0) {
			out += totalCurrencyList.get(0).toString();

			for (int i = 1; i < totalCurrencyList.size(); i++) {
				out += "\n" + totalCurrencyList.get(i).toString();
			}
		} else {
			out = "N/A";
		}
		return out;
	}
	
	
	public void addToTotal(Currency add){
		Currency newAdd = new Currency(add.getType());
		double amount = add.getAmount();
		
		int i;
		for (i = 0; i<this.totalCurrencyList.size(); i++){
			if (this.totalCurrencyList.get(i).getType().equals(add.getType())){
				amount += this.totalCurrencyList.get(i).getAmount();
				newAdd.setAmount(amount);
				this.totalCurrencyList.remove(i);
			}
		}
		
		if (amount != 0) {
			if (amount == add.getAmount()) {
				newAdd.setAmount(amount);
				this.totalCurrencyList.add(newAdd);
			} else {
				this.totalCurrencyList.add(0, newAdd);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<Currency> getTotalCurrency() {
		return totalCurrencyList;
	}

	public void setTotalCurrency(ArrayList<Currency> totalCurrency) {
		this.totalCurrencyList = totalCurrency;
	}

	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(ArrayList<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	@Override
	public String toString(){
		return this.name;
	}
}
