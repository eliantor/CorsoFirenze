package me.aktor.course.droid.services2;

/**
 * Created by eto on 11/12/13.
 */
public class LongRun {

    static void sleep(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch (InterruptedException e){
            Thread.currentThread().isInterrupted();
            return;
        }
    }
}
