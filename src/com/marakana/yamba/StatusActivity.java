package com.marakana.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener{
	private Button buttonTweet;
	private EditText editStatus;
	private TextView textCount;
	private int defaultTextColor;
	private static final String TAG="StatusActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		buttonTweet=(Button)this.findViewById(R.id.buttonTweet);
		editStatus=(EditText)this.findViewById(R.id.editStatus);
		textCount=(TextView)this.findViewById(R.id.textCount);
		buttonTweet.setOnClickListener(this);
		defaultTextColor=editStatus.getTextColors().getDefaultColor();
		editStatus.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int count=140-editStatus.length();
				textCount.setText(Integer.toString(count));
				if(count>20&&count<=140){
					textCount.setTextColor(Color.GREEN);
				}else if(count>=0&&count<=20){
					textCount.setTextColor(Color.YELLOW);
				}else {
					textCount.setTextColor(Color.RED);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String status=editStatus.getText().toString();
		Log.d(TAG, "onClick with status:"+status);
		new PostTask().execute(status);
	}
	private final class PostTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			YambaClient yambaCloud=new YambaClient("student", "password");
			try {
				yambaCloud.postStatus(params[0]);
				return "Successfully post";
			} catch (YambaClientException e) {
				// TODO: handle exception
				e.printStackTrace();
				return "Failed to post to yamba service";
			}
		}
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_SHORT).show();
		}
		
	}

}
