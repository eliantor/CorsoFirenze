package me.aktor.course.droid.dynamic.fragment;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.aktor.course.droid.R;

public class DynamicFargmentActivity extends ActionBarActivity {

    private final static String TAG = "PLACEHOLDER";
    private final static String ALT = "ALTERNATE";

    private final static String TAG_TEST = "TEST";

    private final static String TAG_TEST_RETAINED = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fargment);
        Log.d("LOGGING","I' m activity "+System.identityHashCode(this));
        if (savedInstanceState == null) {
            FragmentManager manager =getSupportFragmentManager();
            PlaceholderFragment sp = new PlaceholderFragment();
            sp.setMessage("Placeholder");
            manager.beginTransaction()
                   .add(R.id.container, new PlaceholderFragment(), TAG)
                   .commit();

            RetainedFragment rt = new RetainedFragment();
            rt.setMessage("Retained");
            manager.beginTransaction()
                    .add(rt,TAG_TEST_RETAINED)
                    .commit();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dynamic_fargment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_change){
            changeFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AlternateFragment(), ALT)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
    }

}
