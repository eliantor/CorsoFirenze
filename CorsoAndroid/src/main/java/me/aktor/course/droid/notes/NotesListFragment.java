package me.aktor.course.droid.notes;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.aktor.course.droid.NotesContract;
import me.aktor.course.droid.R;

/**
 * Created by eto on 10/12/13.
 */
public class NotesListFragment extends Fragment {
    private final static int LOADER_ID = R.id.NOTES_LOADER;

    private final static String[] PROJECTION = {
            NotesContract.Note._ID,
            NotesContract.Note.TITLE,
            NotesContract.Note.CONTENT,
            NotesContract.Note.DATE
    };

    private NotesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment,container,false);
        ListView lv = (ListView)v.findViewById(R.id.list);
        mAdapter = new NotesAdapter(getActivity());
        lv.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID,null,fCallbacks);
    }

    private final LoaderManager.LoaderCallbacks<Cursor> fCallbacks
           = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
            if (id == LOADER_ID){
              return  new CursorLoader(getActivity(), NotesContract.Note.CONTENT_URI,null,null,null,null);
            } else {
                throw new RuntimeException("Wrong id");
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
            if (cursorLoader.getId()== LOADER_ID){
                reload(cursor);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {
            reload(null);
        }
    };

    public void reload(Cursor cursor){
        mAdapter.swapCursor(cursor);
    }

    private static class NotesAdapter extends CursorAdapter {

        private LayoutInflater mInflater;

        public NotesAdapter(Context context) {
            super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View v = mInflater.inflate(R.layout.notes_row,viewGroup,false);
            ViewHolder h = new ViewHolder();
            h.title = (TextView)v.findViewById(R.id.note_row_title);
            h.content = (TextView)v.findViewById(R.id.note_row_content);
            h.date = (TextView)v.findViewById(R.id.note_row_date);
            v.setTag(h);
            return v;
        }

        private static class ViewHolder{
            TextView title;
            TextView content;
            TextView date;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder h = (ViewHolder) view.getTag();
            h.title.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Note.TITLE)));
            h.content.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Note.CONTENT)));
            long time = cursor.getLong(cursor.getColumnIndex(NotesContract.Note.DATE));
//            h.date.setText(date);
        }
    }
}
