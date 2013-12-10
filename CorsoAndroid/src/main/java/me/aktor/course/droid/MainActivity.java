package me.aktor.course.droid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.aktor.course.droid.dynamic.fragment.DynamicFargmentActivity;
import me.aktor.course.droid.lists.ListActivity;

/**
 * Created by eto on 03/12/13.
 */
public class MainActivity extends Activity {
    private final static int REQUEST_CODE = 1;

    private final static String SAVE_KEY = "last_text";

    private TextView mOutput;

    private TextView mResult;
    private EditText mInitialInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // associamo layout ad activity
        setContentView(R.layout.main_layout);
        View button = findViewById(R.id.btn_change);
        button.setOnClickListener(fClickListener);
        mOutput=(TextView) findViewById(R.id.tv_output);

        mResult = (TextView) findViewById(R.id.tv_result);
        mInitialInput = (EditText)findViewById(R.id.edt_input);
        findViewById(R.id.btn_submit).setOnClickListener(fClickListener);

        findViewById(R.id.start_list).setOnClickListener(fClickListener);
        findViewById(R.id.start_dynamic).setOnClickListener(fClickListener);

        if (savedInstanceState!=null){
            mOutput.setText(savedInstanceState.getString(SAVE_KEY));
        }



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_KEY,mOutput.getText().toString());
    }


    private String getCurrentInput(){
        return mInitialInput.getText().toString();
    }

    private void startNextActivity(String text){
        if (TextUtils.isEmpty(text)) text = null;
        Intent message = new Intent(this,EditActivity.class);

        message.putExtra(EditActivity.EXTRA_PARAM,text);
        startActivityForResult(message, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_OK){
                if (data!=null){
                    String concatenatedText = data.getStringExtra(EditActivity.COMPOSED_STRING);
                    mResult.setText(concatenatedText);
                }
            } else {
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }
        } else {
           super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private final View.OnClickListener fClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_change:
                    mOutput.setText(R.string.alternate);
                    break;
                case R.id.btn_submit:
                    String text = getCurrentInput();
                    startNextActivity(text);
                    break;
                case R.id.start_list:
                    startActivity(new Intent(MainActivity.this,
                                             ListActivity.class));
                    break;

                case R.id.start_dynamic:
                    startActivity(new Intent(MainActivity.this,
                            DynamicFargmentActivity.class));
                    break;
            }
        }
    };

}
