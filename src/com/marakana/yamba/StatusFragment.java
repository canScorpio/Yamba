package com.marakana.yamba;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment implements OnClickListener {
	private Button buttonTweet;
	private EditText editStatus;
	private TextView textCount;
	private int defaultTextColor;
	private static final String TAG = "StatusActivity";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveInstanceState) {
		View view = inflater
				.inflate(R.layout.activity_status, container, false);
		buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
		editStatus = (EditText) view.findViewById(R.id.editStatus);
		textCount = (TextView) view.findViewById(R.id.textCount);
		buttonTweet.setOnClickListener(this);
		defaultTextColor = editStatus.getTextColors().getDefaultColor();
		editStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
				int count = 140 - editStatus.length();
				textCount.setText(Integer.toString(count));
				if (count > 20 && count <= 140) {
					textCount.setTextColor(Color.GREEN);
				} else if (count >= 0 && count <= 20) {
					textCount.setTextColor(Color.YELLOW);
				} else {
					textCount.setTextColor(Color.RED);
				}
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String status = editStatus.getText().toString();
		Log.d(TAG, "onClick with status:" + status);
		new PostTask().execute(status);
	}

	private final class PostTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				String username = prefs.getString("username", "");
				String password = prefs.getString("password", "");
				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
					getActivity().startActivity(
							new Intent(getActivity(), SettingsActivity.class));
					return "Please update your password and username";
				}
				YambaClient yambaCloud = new YambaClient(username, password);
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
			Toast.makeText(StatusFragment.this.getActivity(), result,
					Toast.LENGTH_SHORT).show();
		}

	}
}
