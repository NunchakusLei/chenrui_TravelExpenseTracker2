package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class ShowClaimListActivity extends Activity{
	// Attributes
		private static final String FILENAME = "claimdata.sav";
		public static ArrayList<Claim> ClaimList;
		public static ClaimAdapter ClaimListAdapter;
		private ListView ClaimListListView;
		public static Claim OpenedClaim;
		public static int OpenedClaimPosition;
		public static boolean isAClaimOpened = false;
		
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.show_claim_list, menu);
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
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_show_claim_list);
			
			ClaimListListView = (ListView) findViewById(R.id.ShowClaimsListView);
			ClaimListListView.setOnItemLongClickListener(new ClaimLongClickListener());
			ClaimListListView.setOnItemClickListener(new ClaimClickListener());
			
			// set up button and button listener
			Button addButton = (Button)findViewById(R.id.ShowClaimsAddButton);
			ButtonListener addButtonListner = new ButtonListener();
			addButton.setOnClickListener((android.view.View.OnClickListener) addButtonListner);
		}
		
		// reaction of claim list click
		class ClaimClickListener implements OnItemClickListener{
			 @Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				
				Intent intent = new Intent(ShowClaimListActivity.this, ShowExpenseListActivity.class);
				OpenedClaim = ClaimList.get(position);
				OpenedClaimPosition = position;
				isAClaimOpened = true;
				
				//System.out.println(isAClaimOpened);
				
				startActivity(intent); 
			 }
		}
		
		// reaction of claim list long click
		class ClaimLongClickListener implements OnItemLongClickListener{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				//https://github.com/jeremykid/Weijie2_Travel-expense-tracking/blob/master/src/ca/ualberta/cs/travel/MainActivity.java 2015.1.29.
				final int deleteIndex = position;
	        	
				AlertDialog.Builder adb = new AlertDialog.Builder(ShowClaimListActivity.this);
				adb.setMessage("Delete "+"\""+ClaimList.get(position)+"\""+"?");
				adb.setCancelable(true);
				
				adb.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
								ClaimList.remove(deleteIndex);
								ClaimListAdapter.notifyDataSetChanged();
								saveInFile();
				}
				});
				adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					@Override
						public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				});
				//Toast.makeText(ListStudentsActivity.this, "Is the on click working?", Toast.LENGTH_SHORT).show();
				adb.show();
				return true;
			}
		}
		
		// reaction of  Button
		class ButtonListener implements View.OnClickListener{
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ShowClaimListActivity.this, AddClaimActivity.class);
				isAClaimOpened = false;
				startActivity(intent);
			}
		}
		
		@Override
		protected void onStart() {
			super.onStart();
			//isAClaimOpened = false;
			
			ClaimList = loadFromFile();
			/*
			Claim test = new Claim();
			//test.setName("testng");
			test.setStartDate(new Date(System.currentTimeMillis()));
			test.setEndDate(new Date(System.currentTimeMillis()));
			test.addToTotal(new Currency("CAD $",0.0));
			test.addToTotal(new Currency("CA $",0.0));
			test.addToTotal(new Currency("USD $",60.0));
			test.addToTotal(new Currency("UD $",60.0));
			ClaimList.clear();
			ClaimList.add(test);
			
			System.out.println(test.TotalCurrencyListToString());
			System.out.println(test.getTotalCurrency().get(0));
			System.out.println(new Currency("USD $",60.0));
			System.out.println(test.getTotalCurrency().get(1));
			System.out.println(new Currency("CAD $",60.0));*/
			
			// set up claim listing adapter
			ClaimListAdapter = new ClaimAdapter(this,R.layout.list_claim, ClaimList);
			ClaimListListView.setAdapter(ClaimListAdapter);
			ClaimListAdapter.notifyDataSetChanged();
		}
		
		
		
		// custom claim adapter
		public class ClaimAdapter extends ArrayAdapter<Claim> {
			// constructor
			public ClaimAdapter(Context context, int resource,List<Claim> objects) {
				super(context, resource, objects);
			}
			
			@Override
		    public View getView(int position, View view, ViewGroup parent) {
		       // Get the data item for this position
				Claim claim = getItem(position);    
				
		       // Check if an existing view is being reused, otherwise inflate the view
		       if (view == null) {
		    	   view = LayoutInflater.from(getContext()).inflate(R.layout.list_claim, parent, false);
		       }
		       
		       // Lookup view for data population
		       TextView listClaimStartDateTextView = (TextView) view.findViewById(R.id.listClaimStartDateTextView);
		       TextView listClaimEndDateTextView = (TextView) view.findViewById(R.id.listClaimEndDateTextView);
		       TextView listClaimNameTextView = (TextView) view.findViewById(R.id.listClaimNameTextView);
		       TextView listClaimTotalListView = (TextView) view.findViewById(R.id.listClaimTotalCurrencyTextView);
		       TextView listClaimStatusTextView = (TextView) view.findViewById(R.id.listClaimStatusTextView);
		       
		       // Populate the data into the template view using the data object
		       listClaimStartDateTextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(claim.getStartDate()));
		       listClaimEndDateTextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(claim.getEndDate()));
		       listClaimTotalListView.setText(claim.TotalCurrencyListToString());
		       listClaimNameTextView.setText(claim.getName());
		       listClaimStatusTextView.setText("Status: "+claim.getStatus());

		       // Return the completed view to render on screen
		       return view;
		   }
		}
		
		
		
		
		
		
		
		
		
		private ArrayList<Claim> loadFromFile() {	
			Gson gson = new Gson();

			ArrayList<Claim> ClaimList = new ArrayList<Claim>();
			try {
				FileInputStream fis = openFileInput(FILENAME);
				Type listType = new TypeToken<ArrayList<Claim>>() {}.getType();
				InputStreamReader isr = new InputStreamReader(fis);
				
				ClaimList = gson.fromJson(isr, listType);
				fis.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(ClaimList==null){
				ClaimList = new ArrayList<Claim>();
			}
			
			return ClaimList;
		}
		
		

		
		public void saveInFile() {
			Gson gson = new Gson();
			
			try {
				FileOutputStream fos = openFileOutput(FILENAME,0);
				
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				gson.toJson(ClaimList,osw);

				osw.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	public void emailAction(MenuItem menu)
	{

		StringBuffer mailBody = new StringBuffer();
		for (int i = 0; i < ClaimList.size(); i++)
		{
			mailBody.append("ClaimName" + ClaimList.get(i).getName() + "\n From"
					+ ClaimList.get(i).getStartDate().toString() + "To"
					+ ClaimList.get(i).getEndDate().toString() + "is"
					+ ClaimList.get(i).getStatus() + "\n description is"
					+ "\n");
		
			
		}
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_TEXT, mailBody.toString());
		try
		{
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex)
		{
			Toast.makeText(ShowClaimListActivity.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
