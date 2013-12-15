package info.u250.digs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class WebDialog extends Dialog {
	public boolean checkNetWorkStatus(Context  ctx) {
		ConnectivityManager connectivity = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity == null) {   
            return false;   
        } else {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
	}
	
	static final float[] DIMENSIONS_DIFF_LANDSCAPE = { 20, 60 };
	static final float[] DIMENSIONS_DIFF_PORTRAIT = { 40, 60 };
	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);
	static final int MARGIN = 15;
	static final int PADDING = 0;
	static final String DISPLAY_STRING = "touch";

	private String mUrl;
	// private DialogListener mListener;
	ProgressDialog mSpinner;
	WebView mWebView;
	private LinearLayout mContent;
	protected TextView mTitle;

	public WebDialog(Context context, String url) {
		super(context);
		if(checkNetWorkStatus(context)){
			mUrl = url;
		}else{
			mUrl = "file:///android_asset/offline.html";
		}
	}

	protected void initWebView(WebView mWebView){
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");

		mContent = new LinearLayout(getContext());
		mContent.setOrientation(LinearLayout.VERTICAL);
		setUpTitle();
		setUpWebView();
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int orientation = getContext().getResources().getConfiguration().orientation;
		float[] dimensions = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? DIMENSIONS_DIFF_LANDSCAPE
				: DIMENSIONS_DIFF_PORTRAIT;
		addContentView(
				mContent,
				new LinearLayout.LayoutParams(display.getWidth()
						- ((int) (dimensions[0] * scale + 0.5f)), display
						.getHeight() - ((int) (dimensions[1] * scale + 0.5f))));
		this.setCancelable(false);
	}

	private void setUpTitle() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mTitle = new TextView(getContext());
		mTitle.setText("My Dragon");
		mTitle.setTextColor(Color.WHITE);
		mTitle.setTypeface(Typeface.DEFAULT_BOLD);
		mTitle.setBackgroundResource(R.drawable.dialog_bg_repeated);
		mTitle.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN);
//		Drawable icon = getContext().getResources().getDrawable(
//				R.drawable.ic_launcher);
//		mTitle.setCompoundDrawablePadding(MARGIN + PADDING);
//		mTitle.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
		mContent.addView(mTitle);
	}

	protected Button button ;
	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView() {
		mWebView = new WebView(getContext());
		mWebView.setVerticalScrollBarEnabled(true);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new DialogWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);

		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams( new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT, 0.8f));
		button = new Button(getContext());
//		button.setBackgroundResource(R.drawable.selector);
//		button.setText("点击关闭");//zh_CN
//		button.setText("Schließen");//de_DE
//		button.setText("Touche pour fermer");//fr_FR
		button.setText("click to close");//en_US
		button.setBackgroundResource(R.drawable.dialog_bg_repeated);
		mContent.addView(mWebView);
		mContent.addView(button);
		
		this.initWebView(mWebView);
	}

	class DialogWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			WebDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			String title = mWebView.getTitle();
			if (title != null && title.length() > 0) {
				mTitle.setText(title);
			}
			mSpinner.dismiss();
		}

	}
}
