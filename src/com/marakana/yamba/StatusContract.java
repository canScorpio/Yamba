package com.marakana.yamba;

import android.net.Uri;
import android.provider.BaseColumns;
public class StatusContract {
	// DB相关的常数
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "status";
	// Provider的常数
	public static final String AUTHORITY = "com.marakana.yamba.StatusProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE);
	public static final int STATUS_ITEM = 1;
	public static final int STATUS_DIR = 2;
	public static final String STATUS_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.marakana.yamba.provider.status";
	public static final String STATUS_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.marakana.yamba.provider.status";
	public static final String DEFAULT_SORT = Cloumn.CREATE_AT + " DESC";

	public class Cloumn {
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGER = "message";
		public static final String CREATE_AT = "create_at";

	}
}
