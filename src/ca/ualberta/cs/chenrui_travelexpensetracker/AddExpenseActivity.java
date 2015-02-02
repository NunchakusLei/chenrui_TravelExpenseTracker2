package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;




public class AddExpenseActivity extends ShowExpenseListActivity {
	private EditText itemText;
	private EditText amountText;
	private EditText addExpenseDescriptionEditView;
	
	private Spinner addExpenseCurrencySpinner;
	private ArrayAdapter<String> dataCurrencyAdapter;
	private Spinner addExpenseCategorySpinner;
	private ArrayAdapter<String> dataCategoryAdapter;
	
	private DatePicker addExpenseDatePicker;
	private TimePicker addExpenseTimePicker;
	//private Button AddEventDateSettingButton;
	//private Button AddEventTimeSettingButton;
	private Date expenseDate;
	private Expense oldExpense;
	
	private boolean isOpenedinLocal;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
		
		itemText = (EditText)findViewById(R.id.editEventText);
		amountText = (EditText)findViewById(R.id.editAmountText);
		addExpenseDescriptionEditView = (EditText)findViewById(R.id.addExpenseDescriptionEditView);
		
		addExpenseDatePicker = (DatePicker) findViewById(R.id.addExpenseDatePicker);
		addExpenseTimePicker = (TimePicker) findViewById(R.id.addExpenseTimePicker);
		
		/*
		AddEventDateSettingButton = (Button)findViewById(R.id.addEventDateSettingButton);
		ButtonListener AddEventDateSettingButtonListner = new ButtonListener();
		AddEventDateSettingButton.setOnClickListener(AddEventDateSettingButtonListner);
		
		AddEventTimeSettingButton = (Button)findViewById(R.id.addEventTimeSettingButton);
		ButtonListener AddEventTimeSettingButtonListner = new ButtonListener();
		AddEventTimeSettingButton.setOnClickListener(AddEventTimeSettingButtonListner);
		*/
		
		Button comfirmAddButton = (Button)findViewById(R.id.comfirmAddEventButton);
		ButtonListener comfirmAddButtonListner = new ButtonListener();
		comfirmAddButton.setOnClickListener(comfirmAddButtonListner);
		
		
		// For Currency Choice Spinner
		// oldClaim = (ListView)findViewById(R.id.evenListView);
		addExpenseCurrencySpinner = (Spinner) findViewById(R.id.addExpenseCurrencySpinner);
		ArrayList<String> list = new ArrayList<String>();
		list.add("CAD $");
		list.add("USD $");
		list.add("EUR €");
		list.add("GBP £");
		list.add("CNY ¥");
		dataCurrencyAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		addExpenseCurrencySpinner.setAdapter(dataCurrencyAdapter);
		
		addExpenseCategorySpinner = (Spinner) findViewById(R.id.addExpenseCategorySpinner);
		ArrayList<String> listCategory = new ArrayList<String>();
		listCategory.add("air fare");
		listCategory.add("ground transport");
		listCategory.add("vehicle rental");
		listCategory.add("fuel");
		listCategory.add("parking");
		listCategory.add("registration");
		listCategory.add("accommodation");
		listCategory.add("meal");
		listCategory.add("shopping");
		dataCategoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listCategory);
		dataCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		addExpenseCategorySpinner.setAdapter(dataCategoryAdapter);

		
		//expenseListAdapter.notifyDataSetChanged();
		//System.out.println(oldExpense);
		//System.out.println(isAExpenseOpened);
		isOpenedinLocal = isAExpenseOpened;
		if(isAExpenseOpened){
			oldExpense = ShowClaimListActivity.OpenedClaim.getExpenseList().get(OpenedExpensePosition);
			
			itemText.setText(oldExpense.getItem());
			amountText.setText(""+(oldExpense.getAmount()));
			addExpenseDescriptionEditView.setText(oldExpense.getDescription());
			
			addExpenseCategorySpinner.setSelection(listCategory.indexOf(oldExpense.getCategory()));
			addExpenseCurrencySpinner.setSelection(list.indexOf(oldExpense.getCurrency()));
			
			//http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android 2015.1.29.
			setTitle("Edit Expense");
			
			//http://stackoverflow.com/questions/6451837/how-do-i-set-the-current-date-in-a-datepicker 2015.1.29.
			addExpenseDatePicker.updateDate(oldExpense.getDate().getYear()+1900, oldExpense.getDate().getMonth(), oldExpense.getDate().getDate());
			
			//http://stackoverflow.com/questions/5817883/setting-time-and-date-to-date-picker-and-time-picker-in-android 2015.1.29.
			addExpenseTimePicker.setCurrentHour(oldExpense.getDate().getHours());
			addExpenseTimePicker.setCurrentMinute(oldExpense.getDate().getMinutes());
			//setContentView(R.layout.activity_add);
			//addExpenseDatePicker.setV
		}
		        
	}

	
	class ButtonListener implements OnClickListener{
		@SuppressWarnings("deprecation")
		@Override
		public void onClick (View view){
			// code to add save content
			setResult(RESULT_OK);
			double amount = 0;

			Expense expense = new Expense();
			String errorType = "";
				
			if(itemText.getText().toString().length()!=0){
				expense.setItem(itemText.getText().toString());
			} else {
				errorType += "Enter a Item! ";
			}
				
			expense.setCategory(addExpenseCategorySpinner.getSelectedItem().toString());
			expense.setDescription(addExpenseDescriptionEditView.getText().toString());
				
			// set up amount
			try{
				amount = Double.parseDouble(amountText.getText().toString());
			}catch(NumberFormatException e){
				errorType += "  Enter Amount!";
			}
			
			expense.setCurrency(new Currency(addExpenseCurrencySpinner.getSelectedItem().toString(),amount));
			
			
			// set up expenseDate
			expenseDate = new Date();
			// expenseDate = new Date(addExpenseDatePicker.getYear() -
			// 1900,addExpenseDatePicker.getMonth(),addExpenseDatePicker.getDayOfMonth());
			expenseDate.setYear(addExpenseDatePicker.getYear() - 1900);
			expenseDate.setMonth(addExpenseDatePicker.getMonth());
			expenseDate.setDate(addExpenseDatePicker.getDayOfMonth());
			expenseDate.setHours(addExpenseTimePicker.getCurrentHour());
			expenseDate.setMinutes(addExpenseTimePicker.getCurrentMinute());
			/*
			 * System.out.println(expenseDate);
			 * expenseDate.setTime(addExpenseDatePicker.get());
			 * System.out.println(expenseDate);
			 * //expense.setDate(addExpenseDatePicker.getDisplay());
			 * System.out.println(addExpenseDatePicker.getYear());
			 * System.out.println(addExpenseDatePicker.getDayOfMonth());
			 * System.out.println(addExpenseDatePicker.getMonth());
			 */
			expense.setDate(expenseDate);
				
			expense.setChangeDate(new Date(System.currentTimeMillis()));
			
				
			//Date testDate = new Date(System.currentTimeMillis());
			//System.out.println(testDate);
				
				
				
			if (errorType.length() == 0) {		
				//System.out.println(oldExpense);
				ArrayList<Expense> newExpenseList=ShowClaimListActivity.OpenedClaim.getExpenseList();
				
				System.out.println(isOpenedinLocal);
				if (isOpenedinLocal){
					// subtract the old expense amount from total
					Currency oldCurrency = newExpenseList.get(OpenedExpensePosition).getCurrency();
					oldCurrency.setAmount(0-oldCurrency.getAmount());
					ShowClaimListActivity.OpenedClaim.addToTotal(oldCurrency);
					// change expense list
					newExpenseList.remove(OpenedExpensePosition);
					newExpenseList.add(OpenedExpensePosition, expense);
					
				} else {
					newExpenseList.add(0, expense);
				}
				// prepare for saving data
				ShowClaimListActivity.OpenedClaim.setExpenseList(newExpenseList);
				ShowClaimListActivity.OpenedClaim.addToTotal(expense.getCurrency());
				ShowClaimListActivity.ClaimList.remove(ShowClaimListActivity.OpenedClaimPosition);
				ShowClaimListActivity.ClaimList.add(ShowClaimListActivity.OpenedClaimPosition,ShowClaimListActivity.OpenedClaim);
				isOpenedinLocal = false; 
				saveInFile();

				Toast.makeText(getBaseContext(), "Expense added",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getBaseContext(),errorType,Toast.LENGTH_SHORT).show();
			}

		}
	}
		

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	// Add spinner data

	public void addListenerOnSpinnerItemSelection() {

		addExpenseCurrencySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		addExpenseCurrencySpinner = (Spinner) findViewById(R.id.addExpenseCurrencySpinner);

	}
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
	    public void onItemSelected(AdapterView<?> parent, View view, int pos,
	            long id) {

	        /*
	        Toast.makeText(parent.getContext(), 
	                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
	                Toast.LENGTH_SHORT).show();*/
	    }
	 
	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	}
}
