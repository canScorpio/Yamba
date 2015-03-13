package com.marakana.yamba;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	private TextView textUser, textMessage, textCreatedAt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.list_item, null, false);
		textUser = (TextView) view.findViewById(R.id.list_item_text_user);
		textMessage = (TextView) view.findViewById(R.id.list_item_text_message);
		textCreatedAt = (TextView) view
				.findViewById(R.id.list_item_text_created_at);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		long id = getActivity().getIntent().getLongExtra(
				StatusContract.Cloumn.ID, -1);
		updateView(id);
	}

	public void updateView(long id) {
		if (id == -1) {
			textUser.setText("");
			textMessage.setText("");
			textCreatedAt.setText("");
			return;
		}
		Uri uri = ContentUris.withAppendedId(StatusContract.CONTENT_URI, id);
		Cursor cursor = getActivity().getContentResolver().query(uri, null,
				null, null, null);
		if (!cursor.moveToFirst()) {
			return;
		}
		String user = cursor.getString(cursor
				.getColumnIndex(StatusContract.Cloumn.USER));
		String message = cursor.getString(cursor
				.getColumnIndex(StatusContract.Cloumn.MESSAGER));
		String createdAtString = cursor.getString(cursor
				.getColumnIndex(StatusContract.Cloumn.CREATE_AT));
		Date date=new Date(Long.parseLong(createdAtString));
		SimpleDateFormat sdf=new SimpleDateFormat("yy/MM/dd HH:mm");
		String createdAt=sdf.format(date);
		textUser.setText(user);
		textMessage.setText(message);
		textCreatedAt.setText(createdAt);
	}

}
