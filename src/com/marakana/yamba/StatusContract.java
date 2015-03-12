package com.marakana.yamba;

import android.provider.BaseColumns;

public class StatusContract {
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "status";
	public static final String DEFAULT_SORT = Cloumn.CREATE_AT + "DESC";

	public class Cloumn {
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGER = "message";
		public static final String CREATE_AT = "create_at";

	}
}
