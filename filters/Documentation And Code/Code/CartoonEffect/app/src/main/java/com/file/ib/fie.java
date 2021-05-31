package com.file.ib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cambe.cartooneffecteditor.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class fie {

	Context Contextt;
	InterstitialAd InterstialAd;
	RelativeLayout relative;
	String Interstial, Interstial1, Interstial2;
	String Banner, Banner1, Banner2;

	ConnectivityManager connec;

	Context aContext;
	String Packages;
	String Name;

	boolean ads_exit_Preferense, ads_enter_Preferense;

	String Enter_Ads_Popup_Or_Not = "", Exit_Top_Ads_Popup_Or_Not = "";

	LinearLayout LL;
	HorizontalScrollView HH;

	Dialog Ad_Dialog;

	@SuppressWarnings("static-access")
	public fie(Context context, String Package, String name,
			final String Interstial_ID, final String Interstial_ID1,
			final String Banner_ID, final String Banner_ID1) {

		aContext = context;
		Packages = Package;
		Name = name;

		Contextt = context;
		Interstial1 = Interstial_ID;
		Interstial2 = Interstial_ID1;

		Banner1 = Banner_ID;
		Banner2 = Banner_ID1;


		connec = (ConnectivityManager) Contextt
				.getSystemService(Contextt.CONNECTIVITY_SERVICE);

	}

	public void Exit(Context aContext) {

		Exit("Are you sure to Exit!", aContext);

	}

	@SuppressLint({ "InflateParams", "InlinedApi" })
	public void Exit(String string, final Context context) {

		final Dialog exitDialog = new Dialog(context);
		exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = (View) inflater.inflate(R.layout.exit_ads_layout,
				null);
		exitDialog.setContentView(convertView);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(exitDialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.gravity = Gravity.CENTER;
		exitDialog.getWindow().setAttributes(lp);

		exitDialog.setCancelable(true);
		exitDialog.setCanceledOnTouchOutside(true);
		GridView lvExitAds = (GridView) convertView
				.findViewById(R.id.lvExitAds);
		TextView tvHeading = (TextView) convertView
				.findViewById(R.id.tvHeading);
		TextView tvNoLater = (TextView) convertView
				.findViewById(R.id.tvNoLater);
		TextView Developer_Title = (TextView) convertView
				.findViewById(R.id.Developer_Title);
		TextView tvRateUs = (TextView) convertView.findViewById(R.id.tvRateUs);
		TextView tvYes = (TextView) convertView.findViewById(R.id.tvYes);
		tvHeading.setText(string);
		tvNoLater.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				exitDialog.dismiss();
			}
		});

		tvRateUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Uri uri = Uri.parse("market://details?id=" + Packages);
				Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);

				try {

					context.startActivity(myAppLinkToMarket);

				} catch (ActivityNotFoundException e) {

					// the device hasn't installed Google Play
					Toast.makeText(context,
							"you don't have Google play installed",
							Toast.LENGTH_LONG).show();

				}
			}

		});
		tvYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				exitDialog.dismiss();
				((Activity) context).moveTaskToBack(true);
				((Activity) context).finish();
			}
		});
		if (ads_exit_Preferense == false) {
			lvExitAds.setVisibility(View.GONE);

		} else {
			lvExitAds.setVisibility(View.VISIBLE);
			Developer_Title.setVisibility(View.VISIBLE);

		}
		exitDialog.show();

	}

	public void DidTapButton(Context context, View view) {
		final Animation myAnim = AnimationUtils.loadAnimation(context,
				R.anim.bounce);

		MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
		myAnim.setInterpolator(interpolator);

		view.startAnimation(myAnim);
	}

	class MyBounceInterpolator implements android.view.animation.Interpolator {
		private double mAmplitude = 1;
		private double mFrequency = 10;

		MyBounceInterpolator(double amplitude, double frequency) {
			mAmplitude = amplitude;
			mFrequency = frequency;
		}

		public float getInterpolation(float time) {
			return (float) (-1 * Math.pow(Math.E, -time / mAmplitude)
					* Math.cos(mFrequency * time) + 1);
		}
	}

	// Google.................
	// Splasd Declaration. . . . .
	public void Splash_Screen(final Context mContext, boolean bool,
			final int Splash_Screen_Time) {

		if (filesharedpref.getsplashcount(mContext) >= 10) {

		} else {


			if (bool == true) {

				new Handler().postDelayed(new Runnable() {

					/*
					 * Showing splash screen with a timer. This will be useful
					 * when you want to show case your app logo / company
					 */

					@Override
					public void run() {
						// This method will be executed once the timer is over
						// Start your app main activity

						Load_Splash_Goog(mContext, Splash_Screen_Time);

					}

				}, Splash_Screen_Time * 1000);

			} else {

			}

		}

	}

	private void Load_Splash_Goog(final Context mContext, int splash_Screen_Time) {
		// TODO Auto-generated method stub

		Ad_Popup(mContext);

		if (filesharedpref.getinter(mContext) == 0) {

			Interstial = Interstial1;

			filesharedpref.setinter(mContext, 1);

		} else {

			Interstial = Interstial2;

			filesharedpref.setinter(mContext, 0);

		}

		try {

			AdRequest adRequest = new AdRequest.Builder().build();
			InterstialAd = new InterstitialAd(mContext);
			InterstialAd.setAdUnitId(Interstial);

			InterstialAd.loadAd(adRequest);

			InterstialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {

					InterstialAd.show();
					filesharedpref.setsplashcount(mContext, (0));
					Ad_Dialog.dismiss();

				}

				@Override
				public void onAdFailedToLoad(int errorCode) {

					Ad_Dialog.dismiss();

				}

			});
		} catch (Exception e) {

		}

	}

	public void Interstial(final Context mContext, final int Interstial_Time) {
		// TODO Auto-generated method stub

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity

				Intrestial(mContext, Interstial_Time);

			}

		}, Interstial_Time * 1000);

	}

	private void Intrestial(final Context mContext, int Interstial_Time) {
		// TODO Auto-generated method stub

		Ad_Popup(mContext);

		if (filesharedpref.getinter(mContext) == 0) {

			Interstial = Interstial1;

			filesharedpref.setinter(mContext, 1);

		} else {

			Interstial = Interstial2;

			filesharedpref.setinter(mContext, 0);

		}

		try {

			AdRequest adRequest = new AdRequest.Builder().build();
			InterstialAd = new InterstitialAd(mContext);
			InterstialAd.setAdUnitId(Interstial);

			InterstialAd.loadAd(adRequest);

			InterstialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {

					InterstialAd.show();
					filesharedpref.setsplashcount(mContext, (0));
					Ad_Dialog.dismiss();

				}

				@Override
				public void onAdFailedToLoad(int errorCode) {

					Ad_Dialog.dismiss();



				}

			});
		} catch (Exception e) {

		}

	}

	// Load Google Banner Ads
	public void Banner(final RelativeLayout Ad_Layout, int Banner_Type) {

		AdSize Banner_Type_Size = null;

		if (Banner_Type == 1) {

			Banner_Type_Size = AdSize.SMART_BANNER;

		} else if (Banner_Type == 2) {

			Banner_Type_Size = AdSize.LARGE_BANNER;

		} else if (Banner_Type == 3) {

			Banner_Type_Size = AdSize.MEDIUM_RECTANGLE;

		} else {

			Banner_Type_Size = AdSize.SMART_BANNER;

		}

		if (filesharedpref.getbanner(Contextt) == 0) {

			Banner = Banner1;

			filesharedpref.setbanner(Contextt, 1);

		} else {

			Banner = Banner2;

			filesharedpref.setbanner(Contextt, 0);

		}

		AdView mAdView = new AdView(Contextt);
		mAdView.setAdSize(Banner_Type_Size);
		mAdView.setAdUnitId(Banner);
		AdRequest adre = new AdRequest.Builder().build();
		mAdView.loadAd(adre);
		Ad_Layout.addView(mAdView);

		RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) Ad_Layout
				.getLayoutParams();
		relativeParams.setMargins(0, 10, 0, 0); // left, top, right, bottom
		Ad_Layout.setLayoutParams(relativeParams);

		mAdView.setAdListener(new AdListener() {

			@Override
			public void onAdLoaded() {
				// TODO Auto-generated method stub

				Ad_Layout.setVisibility(View.VISIBLE);
				filesharedpref.setsplashcount(Contextt, (0));

				super.onAdLoaded();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				// TODO Auto-generated method stub
				DisplayMetrics metrics = Contextt.getResources()
						.getDisplayMetrics();

				if ((metrics.heightPixels) > (metrics.widthPixels)) {

					Ad_Layout.getLayoutParams().height = (int) (metrics.heightPixels / 10);

				} else if ((metrics.widthPixels) > (metrics.heightPixels)) {

					Ad_Layout.getLayoutParams().height = (int) (metrics.heightPixels / (6.5));

				} else {

					Ad_Layout.getLayoutParams().height = (int) (metrics.heightPixels / 10);

				}



				filesharedpref.setsplashcount(Contextt,
						(filesharedpref.getsplashcount(Contextt) + 1));

			}
		});

	}

	// Load & Show Google Intrestial Ads
	public void Interstial(final Context mContext) {

		if (filesharedpref.getinter(mContext) == 0) {

			Interstial = Interstial1;

			filesharedpref.setinter(mContext, 1);

		} else {

			Interstial = Interstial2;

			filesharedpref.setinter(mContext, 0);

		}

		Ad_Popup(mContext);

		try {

			AdRequest adRequest = new AdRequest.Builder().build();
			InterstialAd = new InterstitialAd(mContext);
			InterstialAd.setAdUnitId(Interstial);

			InterstialAd.loadAd(adRequest);

			InterstialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {

					Ad_Dialog.dismiss();
					InterstialAd.show();
					filesharedpref.setsplashcount(mContext, (0));

				}

				@Override
				public void onAdFailedToLoad(int errorCode) {

					Ad_Dialog.dismiss();



				}

			});
		} catch (Exception e) {

		}

	}

	// Load & Show Google Intrestial Ads
	public void Exit_Interstial() {

		if (filesharedpref.getinter(Contextt) == 0) {

			Interstial = Interstial1;

			filesharedpref.setinter(Contextt, 1);

		} else {

			Interstial = Interstial2;

			filesharedpref.setinter(Contextt, 0);

		}

		Ad_Popup(Contextt);

		try {

			AdRequest adRequest = new AdRequest.Builder().build();
			InterstialAd = new InterstitialAd(Contextt);
			InterstialAd.setAdUnitId(Interstial);

			InterstialAd.loadAd(adRequest);

			InterstialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {

					Ad_Dialog.dismiss();
					InterstialAd.show();
					filesharedpref.setsplashcount(Contextt, (0));

				}

				@Override
				public void onAdFailedToLoad(int errorCode) {

					Ad_Dialog.dismiss();



				}

			});
		} catch (Exception e) {

		}

	}

	// Inter Counted. . . . .
	public void Interstial_Counted(Context mContext, int Ads_Number) {

		if (Ads_Number == filesharedpref.getcount(mContext)) {

			Interstial(mContext);

			filesharedpref.setcount(mContext, 1);

		} else {

			filesharedpref.setcount(mContext,
					(filesharedpref.getcount(mContext) + 1));

		}

	}

	private void Ad_Popup(Context mContext2) {
		// TODO Auto-generated method stub

		Ad_Dialog = new Dialog(mContext2);
		Ad_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Ad_Dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		Ad_Dialog.setContentView(R.layout.load_popup);
		Ad_Dialog.setCancelable(true);
		Ad_Dialog.show();

	}

}