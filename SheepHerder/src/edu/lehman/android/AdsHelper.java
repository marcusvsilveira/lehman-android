package edu.lehman.android;

import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Utility class to help creating and showing ads to the each activity
 * @author marcus.silveira
 *
 */
public class AdsHelper {
	private AdsHelper() {} /* all methos of this class should be static, so no instances should be created */
	
	public static void showAds(AdView mAdView, LinearLayout topLevelLayout) {
		// Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId("ca-app-pub-2245689993612349/6894665915");

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

//        // Add the AdView to the view hierarchy.
//        topLevelLayout.addView(mAdView);

        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());
	}
}
