package com.droidschools.oauthallproviders;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droidschools.oauthallproviders.imageloader.ImageLoader;

public class OauthHome extends Activity implements OnClickListener {

	Button facebookButton, twitterButton, googleButton, yahooButton;
	Button facebookReadprofile, twitterReadProfile, googleReadProfile, yahooReadProfile;
	ImageLoader imageLoader;
	ImageView facebookAvatar, twitterAvatar, googleAvatar, yahooAvatar;
	TextView logText;
	SocialAuthAdapter authAdapter;
	// Currently not used
	String FACEBOOK = "facebook";
	String TWITTER = "twitter";
	String GOOGLE = "google";
	String YAHOO = "yahoo";
	// For logging
	String NULLDATA = "Null data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth_home);
		initViews();
		// Create an adapter using a response listener
		authAdapter = new SocialAuthAdapter(new ResponseListener());
		imageLoader = new ImageLoader(this);
		// Add providers like facebook , Google to the oauth adapter
		addProviders();
	}
	public void onClick(View v) {

		if (v.getId() == R.id.facebookButton) {
			writeLog("Authorizing Facebook");
			authAdapter.authorize(this, Provider.FACEBOOK);
		} else if (v.getId() == R.id.twitterButton) {
			writeLog("Authorizing Twitter");
			authAdapter.authorize(this, Provider.TWITTER);
		} else if (v.getId() == R.id.googleButton) {
			writeLog("Authorizing Google");
			authAdapter.authorize(this, Provider.GOOGLE);
		} else if (v.getId() == R.id.yahooButton) {
			writeLog("Authorizing Yahoo");
			authAdapter.authorize(this, Provider.YAHOO);
		} else if (v.getId() == R.id.facebookProfile) {
			writeLog("Reading Facebook Profile");
			readProfile();

		}

	}
	public class ResponseListener implements DialogListener {

		public void onComplete(Bundle values) {
			writeLog(authAdapter.getCurrentProvider().getProviderId() + " authentication sucess");
			enableProvider(Utility.Providers.valueOf(authAdapter.getCurrentProvider().getProviderId()));
		}

		public void onBack() {
			writeLog("Back pressed ");

		}

		public void onCancel() {
			writeLog("Authentication Cancelled !");

		}

		public void onError(SocialAuthError arg0) {
			// updateLog("Authentication Error !");
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
		facebookReadprofile = (Button) findViewById(R.id.facebookProfile);
		facebookAvatar = (ImageView) findViewById(R.id.facebookavatar);

		// Login buttons
		facebookButton.setOnClickListener(this);
		twitterButton.setOnClickListener(this);
		googleButton.setOnClickListener(this);
		yahooButton.setOnClickListener(this);
		// Facebook buttons
		facebookReadprofile.setOnClickListener(this);
		logText.setMovementMethod(new ScrollingMovementMethod());
		writeLog("Views Initiated");
	}

	public void addProviders() {
		authAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		authAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		authAdapter.addProvider(Provider.GOOGLE, R.drawable.google);
		authAdapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		writeLog("Providers added");

	}
	public void showToast(Object message) {
		if (message != (null))
			Toast.makeText(this, message.toString(), 0).show();
		else
			Toast.makeText(this, NULLDATA, 0).show();
	}

	public void log(Object message) {
		if (message != null)
			Log.d("OauthApp", message.toString());
	}
	public void writeLog(Object message) {
		if (message != null)
			logText.append("\n " + message.toString());
		else
			logText.append("\n " + NULLDATA);

	}

	public void enableProvider(Utility.Providers loggedInProvider) {
		LinearLayout facebookLayout = (LinearLayout) findViewById(R.id.facebookLayout);
		LinearLayout twitterLayout = (LinearLayout) findViewById(R.id.twitterLayout);
		LinearLayout googleLayout = (LinearLayout) findViewById(R.id.googleLayout);
		LinearLayout yahooLayout = (LinearLayout) findViewById(R.id.yahooLayout);

		switch (loggedInProvider) {
			case facebook :
				facebookLayout.setBackgroundColor(Color.GREEN);
				break;
			case twitter :
				twitterLayout.setBackgroundColor(Color.GREEN);
				break;
			case google :
				googleLayout.setBackgroundColor(Color.GREEN);
				break;
			case yahoo :
				yahooLayout.setBackgroundColor(Color.GREEN);
				break;
			default :
				break;
		}

	}

	public void readProfile() {
		Profile userProfile = authAdapter.getUserProfile();
		if (userProfile != null) {
			writeLog("EMail: " + userProfile.getEmail());
			writeLog("Name: " + userProfile.getDisplayName());
			writeLog("DOB: " + userProfile.getDob());
			writeLog("Country: " + userProfile.getCountry());
			writeLog("Gender: " + userProfile.getGender());
			writeLog("Language: " +userProfile.getProfileImageURL());
			if (userProfile.getProfileImageURL() != null)
				imageLoader.DisplayImage(userProfile.getProfileImageURL(), facebookAvatar, false);
			log(userProfile.getProfileImageURL());
		}

		/*
		 * Map<String, String> ContactsList = userProfile.getContactInfo();
		 * 
		 * for (String value : ContactsList.values()) { writeLog(value); }
		 */
	}
}
