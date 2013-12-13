package info.u250.digs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

@SuppressLint({ "HandlerLeak" })
public class AdmobAdControlImpl implements Admob {
	DigActivity activity;
	private static final String XID = "a152a82454d5003";
	protected AdView adView;
	final ImageButton closeButton;
	
	private final int HIDE_ADS = 0;
	private final int SHOW_ADS = 1;
	private final int SHOW_INT = 2;
	protected Handler handler = new Handler()  {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    closeButton.setVisibility(View.GONE);
                    break;
                }
                case SHOW_INT:
                {
                	if (mInterstitial.isLoaded()) {
           	         if(!isSHOW){
           	        	 mInterstitial.show();
           	        	 isSHOW = true;
           	         }
           	     	}
                    break;
                }
            }
        }
    };
    public AdmobAdControlImpl(DigActivity activity){
    	this.activity = activity;
		// Create and setup the AdMob view
        adView = new AdView(activity); // Put in your secret key here
        adView.setAdUnitId(XID);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
        
        mInterstitial = new InterstitialAd(activity);
        mInterstitial.setAdUnitId(XID);
        mInterstitial.loadAd(new AdRequest.Builder().build());

        closeButton = new ImageButton(activity);
        closeButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
 
        
	}
	@Override
	public void show() {
		handler.sendEmptyMessage(SHOW_ADS );
	}

	@Override
	public void hide() {
		handler.sendEmptyMessage( HIDE_ADS);
	}

	
	
	InterstitialAd mInterstitial;
	boolean isSHOW = false;//show once!!!
	@Override
	public void showInterstitial() {
		handler.sendEmptyMessage(SHOW_INT);
	}
	public void attach(){
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,  
				RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        activity.layout.addView(adView, adParams);
        
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,  
				RelativeLayout.LayoutParams.WRAP_CONTENT);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        closeButton.setLayoutParams(lparams);
        activity.layout.addView(closeButton, lparams);
	}
}
