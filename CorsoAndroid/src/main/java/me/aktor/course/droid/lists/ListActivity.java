package me.aktor.course.droid.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.aktor.course.droid.R;

/**
 * Created by eto on 03/12/13.
 */
public class ListActivity extends FragmentActivity implements ListFragment.DataSourceProvider{

    private ListFragment mList;

    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        FragmentManager m = getSupportFragmentManager();
        InputFragment input =(InputFragment)m.findFragmentById(R.id.Input);
        input.setOnNewItemListener(fCallback);
        mList = (ListFragment)m.findFragmentById(R.id.List);
//        mList.setDataSourceProvider(this);
        mData = new ArrayList<String>();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof ListFragment){
            ((ListFragment) fragment).setDataSourceProvider(this);
        }
    }

    private void addItem(String text){
        mData.add(text);
        mList.refresh();
    }

    private InputFragment.OnNewItemListener fCallback =
            new InputFragment.OnNewItemListener(){
                @Override
                public void onNewItem(String text) {
                    addItem(text);
                }
            };

    @Override
    public List<String> getDataSource() {
        return mData;
    }
}
