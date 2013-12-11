package info.u250.digs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

@SuppressLint("HandlerLeak")
public class AdmobAdControlImpl implements Admob {
	private static final String XID = "a152a82454d5003";
	protected AdView adView;
	private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
	protected Handler handler = new Handler()  {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };
    public AdmobAdControlImpl(Activity activity){
		// Create and setup the AdMob view
        adView = new AdView(activity); // Put in your secret key here
        adView.setAdUnitId(XID);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
        
        mInterstitial = new InterstitialAd(activity);
        mInterstitial.setAdUnitId(XID);
        mInterstitial.loadAd(new AdRequest.Builder().build());
	}
	@Override
	public void show() {
		handler.sendEmptyMessage(SHOW_ADS );
	}

	@Override
	public void hide() {
		handler.sendEmptyMessage( HIDE_ADS);
	}

	public AdView getAdView() {
		return adView;
	}
	
	
	private InterstitialAd mInterstitial;
	private boolean isSHOW = false;//show once!!!
	@Override
	public void showInterstitial() {
		 if (mInterstitial.isLoaded()) {
	         if(!isSHOW){
	        	 mInterstitial.show();
	        	 isSHOW = true;
	         }
	     }
	}
}
