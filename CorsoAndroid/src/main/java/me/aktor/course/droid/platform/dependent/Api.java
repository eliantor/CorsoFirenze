package me.aktor.course.droid.platform.dependent;

import android.bluetooth.BluetoothGatt;
import android.os.Build;
/**
 * Created by eto on 04/12/13.
 */
public abstract class Api {

    public static Api get(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return new ModernImpl();
        } else {
            return new DefaultLegacyImpl();
        }


    }


    public abstract int generateId();

}
