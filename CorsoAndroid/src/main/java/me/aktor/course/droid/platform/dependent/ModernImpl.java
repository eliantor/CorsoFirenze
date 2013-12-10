package me.aktor.course.droid.platform.dependent;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by eto on 04/12/13.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class ModernImpl extends DefaultLegacyImpl {

    @Override
    public int generateId() {
        return View.generateViewId();

    }
}
