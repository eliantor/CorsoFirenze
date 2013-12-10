package me.aktor.course.droid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by eto on 03/12/13.
 */
public class EditActivity extends Activity {
    public final static String EXTRA_PARAM ="text_param";
    public final static String COMPOSED_STRING = "result";

    private EditText mEdit;
    private String mParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParam =handleIntent(getIntent());
        if (mParam==null){
            return;
        }

        setContentView(R.layout.edit_layout);
        TextView tv  = (TextView)findViewById(R.id.tv_result);
        mEdit = (EditText)findViewById(R.id.edt_input);

        tv.setText(mParam);

        findViewById(R.id.btn_submit).setOnClickListener(fSendResult);
    }

    private String handleIntent(Intent intent){
        String param=intent.getStringExtra(EXTRA_PARAM);
        if (param==null){
            // Cosa facciamo se il parametro non e' stato passato
            finish();
        }
        return param;
    }

    private final View.OnClickListener fSendResult = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // costruiamo il risultato
            String input  = mEdit.getText().toString();
            String res = String.format("%s %s",mParam,input);


            // restituiamo il risultato all'activity chiamante
            Intent messageBack  = new Intent();
            messageBack.putExtra(COMPOSED_STRING,res);
            setResult(RESULT_OK,messageBack);
            finish();
        }
    };


}
