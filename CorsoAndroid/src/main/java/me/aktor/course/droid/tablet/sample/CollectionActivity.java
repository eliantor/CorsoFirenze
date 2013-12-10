package me.aktor.course.droid.tablet.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class CollectionActivity extends FragmentActivity implements CollectionFragment.OnItemSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_activity);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof CollectionFragment){
            ((CollectionFragment) fragment).setListener(this);
        }
    }

    @Override
    public void onItemSelected(int item) {
        Fragment f =getSupportFragmentManager().findFragmentById(R.id.Detail);
        if(f!=null){
            ((DetailFragment)f).show(item);
        }else {
            startActivity(new Intent(this,DetailActivity.class).putExtra("ITEM",item));
        }
    }
}
