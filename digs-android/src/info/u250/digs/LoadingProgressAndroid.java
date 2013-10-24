package info.u250.digs;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;


public class LoadingProgressAndroid {
	ProgressDialog pDialog;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pDialog.dismiss();
		}
	};

	public LoadingProgressAndroid(DigActivity main) {
		pDialog = new ProgressDialog(main);
		pDialog.setCancelable(false);
		
	}
	
	public void percent(float percent){
		TextView text = (TextView) pDialog.findViewById(R.id.loading);
		text.setText((int)(100*percent)+"%");
	}

	public void showLoading() {
		pDialog.show();
		Window window = pDialog.getWindow();
		window.setContentView(R.layout.loading);
//		ImageView imageView  =(ImageView)window.findViewById(R.id.imageView_dian);
//		imageView.setBackgroundResource(R.anim.animloading);
//		AnimationDrawable rocketAnimation = (AnimationDrawable) imageView.getBackground();
//		rocketAnimation.start();
//		rocketAnimation.setOneShot(false);
	}

	public void hideLoading() {
		handler.sendEmptyMessage(0);
	}

}
