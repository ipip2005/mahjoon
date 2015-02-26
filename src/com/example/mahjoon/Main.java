package com.example.mahjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener{
	private TableData td;
	private TableLayout table;
	private int n;
	private int[] points;
	private TableRow rowPoints;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 if(checkData()){
			createTable();
			return;
		}
		setContentView(R.layout.activity_main);
		Button bok = (Button)findViewById(R.id.buttonOk);
		Button bcc = (Button)findViewById(R.id.buttoncc);
		bok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*RadioButton rb1 = (RadioButton)findViewById(R.id.r1);
				RadioButton rb2 = (RadioButton)findViewById(R.id.r2);
				RadioButton rb4 = (RadioButton)findViewById(R.id.r4);*/
				/*int T=1;
				if (rb1.isChecked()) T=1; else 
					if (rb2.isChecked()) T=2; else 
						if (rb4.isChecked()) T=4;*/
				td=new TableData();
				EditText ete = (EditText)findViewById(R.id.east);
				EditText ets = (EditText)findViewById(R.id.south);
				EditText etw = (EditText)findViewById(R.id.west);
				EditText etn = (EditText)findViewById(R.id.north);
				td.setName("east", ete.getText().toString());
				td.setName("south", ets.getText().toString());
				td.setName("west", etw.getText().toString());
				td.setName("north", etn.getText().toString());
				createTable();
				//Main.this.setContentView(R.layout.table);
			}
			
		});
		bcc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void saveData(){
		try {
			FileOutputStream stream = this.openFileOutput("data.s", MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			oos.writeObject(td);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean checkData(){
		try {
			FileInputStream stream = this.openFileInput("data.s");
			ObjectInputStream ois = new ObjectInputStream(stream);
			td=(TableData)ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("maj","td="+ String.valueOf(td!=null));// + td.getLineNum());
		return td!=null;
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		
		saveData();
		super.onSaveInstanceState(savedInstanceState);
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		
		checkData();
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	public void onPause(){
		Log.i("maj", "saveInstance");
		saveData();
		super.onPause();
	}
	@Override
	public void onResume(){
		Log.i("maj", "restoreInstance");
		checkData();
		super.onResume();
	}
	private void createTable(){
		Log.i("maj","createTable");
		setContentView(R.layout.table);
		table = (TableLayout)findViewById(R.id.TableLayout);
		table.removeAllViews();
		String[] name = td.getName();
		int[][] change = td.getChange();
		points = td.getPoints();
		n = td.getLineNum();
		TableRow rowName = new TableRow(this);
		for (int j=0;j<4;j++){
			EditText et = new EditText(this);
			et.setText(name[j]);
			et.setEms(4);
			et.setGravity(Gravity.CENTER);
			rowName.addView(et, j);
		}
		rowName.setGravity(Gravity.CENTER);
		table.addView(rowName);
		View lv = new View(this);
		lv.setBackgroundColor(Color.BLACK);
		TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,5);
		lp.topMargin=10;
		lp.bottomMargin=10;
		lv.setLayoutParams(lp);
		
		table.addView(lv);
		for (int i=0;i<n;i++){
			TableRow row = new TableRow(this);
			for (int j=0;j<4;j++){
				EditText et = new EditText(this);
				et.setInputType(InputType.TYPE_CLASS_NUMBER);		
				et.setText(String.valueOf(change[i][j]));
				et.setEms(4);
				et.setSelectAllOnFocus(true);
				et.setGravity(Gravity.CENTER);
				et.addTextChangedListener(new GenericTextWatcher(et));
				et.setOnClickListener(Main.this);
				int[] pl = new int[2];
				pl[0]=i;pl[1]=j;
				et.setTag(pl);
				row.addView(et);
			}
			row.setGravity(Gravity.CENTER);
			table.addView(row);
		}
		rowPoints = new TableRow(this);
		for (int j=0;j<4;j++){
			TextView et = new TextView(this);
			et.setText(String.valueOf(points[j]));
			et.setGravity(Gravity.CENTER);
			et.setTag(j);
			rowPoints.addView(et, j);
		}
		rowPoints.setGravity(Gravity.CENTER);
		table.addView(rowPoints);
		
		Button addButton = (Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TableRow row = new TableRow(Main.this);
				for (int j=0;j<4;j++){
					EditText et = new EditText(Main.this);
					et.setInputType(InputType.TYPE_CLASS_NUMBER);		
					et.setText("0");
					et.setEms(4);
					et.setSelectAllOnFocus(true);
					et.setGravity(Gravity.CENTER);
					et.addTextChangedListener(new GenericTextWatcher(et));
					et.setOnClickListener(Main.this);
					int[] pl = new int[2];
					pl[0]=n+1;pl[1]=j;
					et.setTag(pl);
					row.addView(et);
				}
				row.setGravity(Gravity.CENTER);
				table.addView(row, n+2);
				int[] newLine = new int[4];
				td.addInfo(newLine);
				renew();
			}
			
		});
		
		
	}
	public void renew(){
		n = td.getLineNum();
		points = td.getPoints();
		for (int i=0;i<4;i++){
			TextView et = (TextView) rowPoints.findViewWithTag(i);
			et.setText(String.valueOf(points[i]));
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.action_create:
			new AlertDialog.Builder(Main.this).setTitle("当前战局将被舍弃")
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					td=null;
					Main.this.recreate();
				}
			})
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			break;
		case R.id.action_history:
			break;
		case R.id.action_settings:
			break;
		}
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private class GenericTextWatcher implements TextWatcher{

	    private View view;
	    private GenericTextWatcher(View view) {
	        this.view = view;
	    }
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
	        EditText et = (EditText)view;
	        String text = et.getText().toString();
	        int value=0;
	        try{
	        	value = Integer.valueOf(text).intValue();
	        }catch (Exception e){
	        	return;
	        }
	        int[] pl = (int[])et.getTag();
	        td.changeValue(pl[0], pl[1], value);
	        renew();
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText et = (EditText)v;
		if (et.isFocused()){
			String text = et.getText().toString();
			if (text.startsWith("-")){
				text=text.substring(1);
			} else text="-"+text;
			et.setText(text);
		}
	}
}
