package com.marakana.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = DBHelper.class.getSimpleName();

	public DBHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = String.format(
				"create table %s (%s int primary key,%s text,%s text,%s int)",
				StatusContract.TABLE, StatusContract.Cloumn.ID,
				StatusContract.Cloumn.USER, StatusContract.Cloumn.MESSAGER,
				StatusContract.Cloumn.CREATE_AT);
		Log.d(TAG, "onCreate with SQL:"+sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exits"+StatusContract.TABLE);
		onCreate(db);
	}
}
