package com.example.mahjoon;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_DELAY = 1500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent mIntent = new Intent(Splash.this,Main.class);
				Splash.this.startActivity(mIntent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_DELAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
