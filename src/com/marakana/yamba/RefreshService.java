package com.marakana.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	public static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "onStart1");
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String username = prefs.getString("username", " ");
		final String password = prefs.getString("password", " ");
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Please update your username and password",
					Toast.LENGTH_LONG).show();
			return;
		}
		Log.d(TAG, "onStarted2");
		YambaClient cloud = new YambaClient(username, password);
		DBHelper dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		try {
			List<Status> timeline = cloud.getTimeline(20);
			for (Status status : timeline) {
				values.clear();
				values.put(StatusContract.Cloumn.ID, status.getId());
				values.put(StatusContract.Cloumn.USER, status.getUser());
				values.put(StatusContract.Cloumn.MESSAGER, status.getMessage());
				values.put(StatusContract.Cloumn.CREATE_AT, status
						.getCreatedAt().getTime());
				db.insertWithOnConflict(StatusContract.TABLE, null, values,
						SQLiteDatabase.CONFLICT_IGNORE);

			}
		} catch (YambaClientException e) {
			// TODO: handle exception
			Log.d(TAG, "Failed to fetch the timeline",e);
			e.printStackTrace();
		}
		return;
		// Log.d(TAG, "onStarted");
	}

}
