package info.u250.dig_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{
	GooglePlayServiceResolver resolver;
	@Override
	public void onClick(View view) {
	    if (view.getId() == R.id.login) {
	    	resolver.gpsLogin();
	    }else if (view.getId() == R.id.logout) {
	    	resolver.gpsLogout();
	    }else if(view.getId() == R.id.share ){
	    	resolver.momentCompleteThePack2();
	    }
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resolver = new GooglePlayServiceResolverAndroidImpl(this);
		
		setContentView(R.layout.activity_main);
		findViewById(R.id.login).setOnClickListener(this);
		 findViewById(R.id.logout).setOnClickListener(this);  
		 findViewById(R.id.share).setOnClickListener(this);  
	    
	}
}
