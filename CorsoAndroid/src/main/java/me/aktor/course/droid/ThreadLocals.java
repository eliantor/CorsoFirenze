package me.aktor.course.droid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eto on 11/12/13.
 */
public class ThreadLocals {
    private final static ThreadLocal<List<String>>
        THREAD_STRINGS = new ThreadLocal<List<String>>(){
        @Override
        protected List<String> initialValue() {
            return new ArrayList<String>();
        }
    };


    private final static List<String> STRINGS = new ArrayList<String>();

    public static void add(String string){
        STRINGS.add(string);
    }

    public static int count(){
        return STRINGS.size();
    }


    public static void addLocal(String string){
        THREAD_STRINGS.get().add(string);
    }

    public static int countLocal(){
        return THREAD_STRINGS.get().size();
    }
}
