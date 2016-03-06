package com.example.megan.admincomm;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Megan on 02/03/2016.
 */
public class MyCustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String []web;
    private final Integer[] imageID;



    public MyCustomList(Activity context, String[] web,Integer[] imageID) {
        super(context,R.layout.list_design,web);
        this.context = context;
        this.web=web;
        this.imageID=imageID;

    }

    @Override
    public View getView(int pos,View view, ViewGroup parent){
        LayoutInflater inflater= context.getLayoutInflater();
        View row= inflater.inflate(R.layout.list_design, null, true);

        TextView textView = (TextView)row.findViewById(R.id.listText);
        ImageView imgView= (ImageView) row.findViewById(R.id.imgBullet);
        textView.setText(web[pos]);
        imgView.setImageResource(imageID[pos]);

        return row;
    }



}
