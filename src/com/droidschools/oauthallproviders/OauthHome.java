package com.droidschools.oauthallproviders;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OauthHome extends Activity implements OnClickListener {

	Button facebookButton, twitterButton, googleButton, yahooButton;
	TextView logText;
	SocialAuthAdapter authAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth_home);
		initViews();
		// Create an adapter using a response listener
		authAdapter = new SocialAuthAdapter(new ResponseListener());
		// Add providers like facebook , Google to the oauth adapter
		addProviders();
	}
	public void onClick(View v) {

		if (v.getId() == R.id.facebookButton) {
			updateLog("Authorizing Facebook");
			authAdapter.authorize(this, Provider.FACEBOOK);
		} else if (v.getId() == R.id.twitterButton) {
			updateLog("Authorizing Twitter");
			authAdapter.authorize(this, Provider.TWITTER);
		} else if (v.getId() == R.id.googleButton) {
			updateLog("Authorizing Google");
			authAdapter.authorize(this, Provider.GOOGLE);
		} else if (v.getId() == R.id.facebookButton) {
			updateLog("Authorizing Yahoo");
			authAdapter.authorize(this, Provider.YAHOO);
		}

	}

	public class ResponseListener implements DialogListener {

		public void onComplete(Bundle values) {
			updateLog("Authentication Sucess");
			Profile googleProfile = authAdapter.getUserProfile();
			if (googleProfile != null)
				updateLog(googleProfile.getEmail());
		}

		public void onBack() {
			updateLog("Back pressed ");

		}

		public void onCancel() {
			updateLog("Authentication Cancelled !");

		}

		public void onError(SocialAuthError arg0) {
			updateLog("Authentication Error !");
		}

	}

	/*
	 * 
	 * Forbidden area below
	 * 
	 * 
	 * Forbidden area below
	 * 
	 * 
	 * Forbidden area below
	 * 
	 * 
	 * Forbidden area below
	 * 
	 * 
	 * Forbidden area below
	 */

	public void initViews() {
		facebookButton = (Button) findViewById(R.id.facebookButton);
		twitterButton = (Button) findViewById(R.id.twitterButton);
		googleButton = (Button) findViewById(R.id.googleButton);
		yahooButton = (Button) findViewById(R.id.yahooButton);
		logText = (TextView) findViewById(R.id.logTextView);
		facebookButton.setOnClickListener(this);
		twitterButton.setOnClickListener(this);
		googleButton.setOnClickListener(this);
		yahooButton.setOnClickListener(this);
		logText.setMovementMethod(new ScrollingMovementMethod());
		updateLog("Views Initiated");
	}

	public void addProviders() {
		authAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		authAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		authAdapter.addProvider(Provider.GOOGLE, R.drawable.google);
		authAdapter.addProvider(Provider.YAHOO, R.drawable.yahoo);

		updateLog("Providers added");

	}
	String NULLDATA = "Null data";
	public void showToast(Object message) {
		if (message != null)
			Toast.makeText(this, message.toString(), 0).show();
		else
			Toast.makeText(this, NULLDATA, 0).show();

	}

	public void log(Object message) {
		if (message != null)
			Log.d("OauthApp", message.toString());
	}
	public void updateLog(Object message) {
		//if (!message.equals("null") )
			logText.append("\n " + message.toString());
//		else
			logText.append("\n " + NULLDATA);

	}

}
