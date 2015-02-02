package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class AddClaimActivity extends ShowClaimListActivity {
	private EditText addClaimNameEeditText;
	private DatePicker addClaimStartDdatePicker;
	private DatePicker addClaimEndDatePicker;
	private EditText addClaimDescriptionEditText;
	
	private Claim oldClaim;
	
	private boolean isAClaimOpenedLocal;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}
	

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_add_claim);
			
			// set up views
			addClaimNameEeditText = (EditText) findViewById(R.id.addClaimNameEeditText);
			addClaimStartDdatePicker = (DatePicker) findViewById(R.id.addClaimStartDdatePicker);
			addClaimEndDatePicker = (DatePicker) findViewById(R.id.addClaimEndDatePicker);
			addClaimDescriptionEditText = (EditText) findViewById(R.id.addClaimDescriptionEditText);
			// set up button and button listener
			Button addClaimButton = (Button) findViewById(R.id.addClaimButton);
			ButtonListener addClaimButtonListener = new ButtonListener();
			addClaimButton.setOnClickListener(addClaimButtonListener);
			
			
			//System.out.println(isAClaimOpened);
			isAClaimOpenedLocal = isAClaimOpened;
			if(isAClaimOpened){
				oldClaim = OpenedClaim;
				
				addClaimButton.setText("Finish edit claim");
				addClaimNameEeditText.setText(oldClaim.getName());
				addClaimDescriptionEditText.setText(oldClaim.getDescription());
				
				//http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android 2015.1.29.
				setTitle("Edit Claims");
				
				//http://stackoverflow.com/questions/6451837/how-do-i-set-the-current-date-in-a-datepicker 2015.1.29.
				
				addClaimStartDdatePicker.updateDate(
						oldClaim.getStartDate().getYear() + 1900, oldClaim.getStartDate()
								.getMonth(), oldClaim.getStartDate().getDate());
				addClaimStartDdatePicker.updateDate(
						oldClaim.getStartDate().getYear() + 1900, oldClaim.getStartDate()
								.getMonth(), oldClaim.getStartDate().getDate());

				isAClaimOpened = false;
			}
			
		}
		
		class ButtonListener implements View.OnClickListener{
			@Override
			public void onClick(View view) {
				// find informations from views
				String name = addClaimNameEeditText.getText().toString();
				
				int startYear = addClaimStartDdatePicker.getYear();
				int startMonth = addClaimStartDdatePicker.getMonth();
				int startDateOfMonth = addClaimStartDdatePicker.getDayOfMonth();
				Date startDate = new Date(startYear-1900,startMonth,startDateOfMonth);
				//System.out.println(startDate);
				//System.out.println(startDate.getYear());
				
				int endYear = addClaimEndDatePicker.getYear();
				int endMonth = addClaimEndDatePicker.getMonth();
				int endDateOfMonth = addClaimEndDatePicker.getDayOfMonth();
				Date endDate = new Date(endYear-1900,endMonth,endDateOfMonth);
				
				String description = addClaimDescriptionEditText.getText().toString();
				
				// store information 
				Claim claim = new Claim();
				//ArrayList<Currency> totalCurrencyList = new ArrayList<Currency>();
				//ArrayList<Expense> expenseList = new ArrayList<Expense>();
				claim.setName(name);
				claim.setStartDate(startDate);
				claim.setEndDate(endDate);
				claim.setDescription(description);
				claim.setStatus("In Progress");
				
				if(isAClaimOpenedLocal){
					claim.setExpenseList(oldClaim.getExpenseList());
					claim.setTotalCurrency(oldClaim.getTotalCurrency());
					ClaimList.remove(OpenedClaimPosition);
					isAClaimOpenedLocal = false;
					OpenedClaim = claim;
				}
				
				// add new to form sorted claim list
				ClaimList.add(findSortedPosition(claim,ClaimList),claim);
				saveInFile();
				
				
				// show to user
				Toast.makeText(getBaseContext(), "Claim added",Toast.LENGTH_SHORT).show();
				
				finish();
			}
		}
		
		public int findSortedPosition(Claim claim,ArrayList<Claim> ClaimList){
			// list size <= 1
			if (ClaimList.size()==1){
				if (claim.getStartDate().before(ClaimList.get(0).getStartDate())){
					return 1;
				} else {
					return 0;
				}
			} else if (ClaimList.size()==0){
				return 0;
			}
			// else
			int start = 0;
			int end = ClaimList.size();
			int position = (start + end) / 2;
			Date target = claim.getStartDate();
			Date temp = ClaimList.get(position).getStartDate();
			while(start!=(end-1)){
				if (target.equals(temp)){
					return position;
				} else if (target.before(temp)) {
					start = position;
				} else if (target.after(temp)) {
					end = position;
				}
				position = (start + end) / 2;
			}
			
			return end;
		}
	}


