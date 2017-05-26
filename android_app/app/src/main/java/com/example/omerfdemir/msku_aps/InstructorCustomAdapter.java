package com.example.omerfdemir.msku_aps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by omerfdemir on 15.05.2017.
 */

public class InstructorCustomAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Instructor> mCustomList;
    Context context;
    Instructor instructor;
    public InstructorCustomAdapter(Activity activity,List<Instructor> instructor){
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCustomList = instructor;}
    @Override
    public int getCount() {
        return mCustomList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCustomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView instructor_name;
        ImageView instructor_image;
        View rowView = mInflater.inflate(R.layout.list_instructors,null);
        instructor_image = (ImageView) rowView.findViewById(R.id.iv_lv);
        instructor = mCustomList.get(position);
        instructor_name = (TextView) rowView.findViewById(R.id.li_tv_ad);
        instructor_name.setText(instructor.getName().replace("[","").replace("]","").replace("\"",""));
        Glide.with(rowView.getContext()).load(instructor.getImageLocation())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(instructor_image);
        return rowView;

    }
}
