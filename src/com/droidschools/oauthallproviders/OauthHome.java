package com.droidschools.oauthallproviders;

import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.ProgressDialog;
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
	Button readprofile;
	Button readContacts;
	ImageLoader imageLoader;
	ImageView avatarImage;
	TextView logText;
	SocialAuthAdapter authAdapter;
	// Currently not used
	String FACEBOOK = "facebook";
	String TWITTER = "twitter";
	String GOOGLE = "google";
	String YAHOO = "yahoo";
	// For logging
	String NULLDATA = "Null data";
	ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mDialog = new ProgressDialog(this);
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
		} else if (v.getId() == R.id.profileButton) {
			writeLog("Reading User Profile");
			readProfile();

		} else if (v.getId() == R.id.contactsButton) {
			writeLog("Reading User Contacts");
			readContacts();

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
		// Facebook
		readprofile = (Button) findViewById(R.id.profileButton);
		avatarImage = (ImageView) findViewById(R.id.avatarImage);
		readContacts = (Button) findViewById(R.id.contactsButton);
		readprofile.setOnClickListener(this);
		readContacts.setOnClickListener(this);

		// Twitter
		// Login buttons
		facebookButton.setOnClickListener(this);
		twitterButton.setOnClickListener(this);
		googleButton.setOnClickListener(this);
		yahooButton.setOnClickListener(this);
		// Facebook buttons
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
		// Scroll automatically
		if (logText.getLayout() != null) {
			int scrollAmount = logText.getLayout().getLineTop(logText.getLineCount()) - logText.getHeight();

			if (scrollAmount > 0)
				logText.scrollTo(0, scrollAmount);
			else
				logText.scrollTo(0, 0);
		}
	}

	public void enableProvider(Utility.Providers loggedInProvider) {
		LinearLayout facebookLayout = (LinearLayout) findViewById(R.id.facebookLayout);
		LinearLayout twitterLayout = (LinearLayout) findViewById(R.id.twitterLayout);
		LinearLayout googleLayout = (LinearLayout) findViewById(R.id.googleLayout);
		LinearLayout yahooLayout = (LinearLayout) findViewById(R.id.yahooLayout);

		switch (loggedInProvider) {
			case facebook :
				facebookLayout.setBackgroundColor(Color.GREEN);
				twitterLayout.setBackgroundColor(0);
				googleLayout.setBackgroundColor(0);
				yahooLayout.setBackgroundColor(0);

				break;
			case twitter :
				twitterLayout.setBackgroundColor(Color.GREEN);
				facebookLayout.setBackgroundColor(0);
				googleLayout.setBackgroundColor(0);
				yahooLayout.setBackgroundColor(0);

				break;
			case google :
				googleLayout.setBackgroundColor(Color.GREEN);
				facebookLayout.setBackgroundColor(0);
				twitterLayout.setBackgroundColor(0);
				yahooLayout.setBackgroundColor(0);

				break;
			case yahoo :
				yahooLayout.setBackgroundColor(Color.GREEN);
				facebookLayout.setBackgroundColor(0);
				googleLayout.setBackgroundColor(0);
				twitterLayout.setBackgroundColor(0);

				break;
			default :
				break;
		}

	}

	public void readProfile() {
		mDialog.show();
		authAdapter.getUserProfileAsync(new ProfileDataListener());
	}
	public void readContacts() {
		mDialog.show();
		// List<Contact> contactList =
		authAdapter.getContactListAsync(new ContactDataListener());

	}
	private final class ContactDataListener implements SocialAuthListener<List<Contact>> {

		@Override
		public void onExecute(String provider, List<Contact> t) {

			Log.d("Custom-UI", "Receiving Data");
			List<Contact> contactsList = t;

			if (contactsList != null)

				if (contactsList.size() > 0) {
					writeLog("Read " + contactsList.size() + "Contacts. Showing first 2");
					writeLog(contactsList.size() + " Contacts Read");

					writeLog("Logging First two");

					if (contactsList.get(0) != null)
						writeLog(contactsList.get(0).getDisplayName() + ", " + contactsList.get(0).getEmail());
					if (contactsList.get(1) != null)
						writeLog(contactsList.get(1).getDisplayName() + ", " + contactsList.get(1).getEmail());
				} else
					writeLog("Zero contacts found on Server");

		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
	private final class ProfileDataListener implements SocialAuthListener<Profile> {

		@Override
		public void onExecute(String provider, Profile t) {

			Log.d("Custom-UI", "Receiving Data");
			mDialog.dismiss();
			Profile userProfile = t;
			if (userProfile != null) {
				writeLog("----------------------------------------------");
				writeLog("Name: " + userProfile.getDisplayName());
				writeLog("Email: " + userProfile.getEmail());
				writeLog("DOB: " + userProfile.getDob());
				writeLog("Location: " + userProfile.getLocation());
				writeLog("Gender: " + userProfile.getGender());
				writeLog("Language: " + userProfile.getLanguage());
				if (userProfile.getProfileImageURL() != null)

					imageLoader.DisplayImage(userProfile.getProfileImageURL(), avatarImage, false);
				log(userProfile.getProfileImageURL());
			}

		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
}
