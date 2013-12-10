package me.aktor.course.droid.lists;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class ListFragment extends Fragment {
    private final Callbacks fCallbacks  = new Callbacks();

    public static interface DataSourceProvider {
        public List<String> getDataSource();
    }

    private SimpleAdapter mAdapter;
    private DataSourceProvider mDataProvider;
    private ListView mList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DataSourceProvider){
            mDataProvider = (DataSourceProvider)activity;
        } else {
            throw new RuntimeException("Activity must implement "+DataSourceProvider.class);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment,container,false);

        mList = (ListView)v.findViewById(R.id.list);
        mList.setOnItemClickListener(fCallbacks);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SimpleAdapter(getActivity(),mDataProvider.getDataSource());
        mList.setAdapter(mAdapter);
    }

    public void setDataSourceProvider(DataSourceProvider provider){
        mDataProvider = provider;
    }

//    public void addItem(String text){
//        mData.add(text);
//        mAdapter.notifyDataSetChanged();
//    }

    public void refresh(){
        mAdapter.notifyDataSetChanged();
    }

    private class Callbacks implements  AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), String.format("Clicked item %s", mAdapter.getItem(position)),
                    Toast.LENGTH_LONG).show();
        }
    }
}
