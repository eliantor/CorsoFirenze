package me.aktor.course.droid.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.aktor.course.droid.R;

/**
 * Created by eto on 03/12/13.
 */
class SimpleAdapter extends BaseAdapter implements View.OnClickListener{

    private final List<String> fData;
    private LayoutInflater fInflater;
    SimpleAdapter(Context context,List<String> data){
        fData = data;
        fInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (fData==null) return 0;
        return fData.size();
    }

    @Override
    public String getItem(int position) {
        if (fData==null|| position>=fData.size())return null;
        return fData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        fData.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        TextView textView;
        View button;
        // altre view nel layout
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView ==  null){
            convertView = fInflater.inflate(R.layout.row_layout,parent,false);
            h = new ViewHolder();
            h.textView = (TextView)convertView.findViewById(R.id.tv_row);
            h.button = convertView.findViewById(R.id.btn_delete);
            h.button.setOnClickListener(this);
            convertView.setTag(h);
        } else {
            h = (ViewHolder)convertView.getTag();
        }

        String data = getItem(position);
        h.textView.setText(data);
        h.button.setTag(position);
        return convertView;
    }
}
