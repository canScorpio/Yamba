package com.marakana.yamba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimlelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String TAG = TimlelineFragment.class.getSimpleName();
	private static final String[] FROM = { StatusContract.Cloumn.USER,
			StatusContract.Cloumn.MESSAGER, StatusContract.Cloumn.CREATE_AT };
	private static final int[] TO = { R.id.list_item_text_user,
			R.id.list_item_text_message, R.id.list_item_text_created_at };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter mAdapter;
	private static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// TODO Auto-generated method stub
			long timetamp;
			//
			switch (view.getId()) {
			case R.id.list_item_text_created_at:
				timetamp = cursor.getLong(columnIndex);
				CharSequence relTime = DateUtils
						.getRelativeTimeSpanString(timetamp);
				((TextView) view).setText(relTime);
				return true;
			default:
				return false;
			}
		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setEmptyText("Loading data..");
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
				null, FROM, TO, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mAdapter.setViewBinder(VIEW_BINDER);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		DetailsFragment fragment = (DetailsFragment) getFragmentManager()
				.findFragmentById(R.id.frament_details);
		if (fragment != null && fragment.isVisible()) {
		} else {
			startActivity(new Intent(getActivity(), DetailsActivity.class)
					.putExtra(StatusContract.Cloumn.ID, id));
		}
	}

	class TimelineViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// TODO Auto-generated method stub
			if (view.getId() != R.id.list_item_text_created_at)
				return false;
			long timetamp = cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils
					.getRelativeTimeSpanString(timetamp);
			((TextView) view).setText(relativeTime);
			return true;
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		if (id != LOADER_ID)
			return null;
		Log.d(TAG, "onCreateLoader");
		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		DetailsFragment fragment = (DetailsFragment) getFragmentManager()
				.findFragmentById(R.id.frament_details);
		if (fragment != null && fragment.isVisible() && cursor.getCount() == 0) {
			fragment.updateView(-1);
			Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
		}
		Log.d(TAG, "onLoadFinished with cursor:" + cursor.getCount());
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}
}
