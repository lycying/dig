package info.u250.digs;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
public class E4 extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VideoView video = new VideoView(this) {
			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				int width = View.MeasureSpec.getSize(widthMeasureSpec);
				int height = View.MeasureSpec.getSize(heightMeasureSpec);
				setMeasuredDimension(width, height);
			}
		};
		video.setVideoPath("android.resource://info.u250.digs/" + R.raw.e4);
		video.start();
		
		video.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				redirectTo();
			}
		});
		setContentView(video);
	}
	
	void redirectTo(){        
        Intent intent = new Intent(this, DigActivity.class);
        startActivity(intent);
        finish();
    }
}
