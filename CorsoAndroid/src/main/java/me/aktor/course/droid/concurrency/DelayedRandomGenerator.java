package me.aktor.course.droid.concurrency;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by eto on 04/12/13.
 */
public class DelayedRandomGenerator extends AsyncTask<Long,String,String> {

    public static interface OnComplete{
        public void onMessage(String message);
    }

    private final Random fRand = new Random();
    private final OnComplete mCompleteListener;
    private final WeakReference<OnComplete> mCompletionWeakRef;
    private final boolean mUseWeak;
    DelayedRandomGenerator(OnComplete complete,boolean useWeak){
        if(useWeak){
            mCompletionWeakRef = new WeakReference<OnComplete>(complete);
            mCompleteListener = null;
        } else {
            mCompleteListener = complete;
            mCompletionWeakRef = null;
        }
        mUseWeak = useWeak;
    }

    public static DelayedRandomGenerator startWithDelay(OnComplete listener,long delay,boolean useWeak){
        DelayedRandomGenerator gen = new DelayedRandomGenerator(listener,useWeak);
        gen.execute(delay);
        return gen;
    }

    @Override
    protected String doInBackground(Long... params) {
        long delay = params[0];
        try {
            Thread.sleep(delay);

        String message = String.format("%s %d","Generated ",fRand.nextLong());
        Log.d("ASYNC",message);
        return message;
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null)return;
        if (mUseWeak){

            final OnComplete ref = mCompletionWeakRef.get();
            if (ref!=null){
                ref.onMessage(s);
            }

        }else{
            mCompleteListener.onMessage(s);
        }
    }
}
