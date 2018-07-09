package com.test.user.Seat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.user.R;

import java.util.ArrayList;

/**
 * Created by Asus on 4/3/2018.
 */

public class CustomGridViewAdapter2 extends ArrayAdapter<Item2> {

    Context context;
    int layoutResourceId;
    ArrayList<Item2> data = new ArrayList<Item2>();
    int number;

    public CustomGridViewAdapter2(Context context, int layoutResourceId, ArrayList<Item2> data,int number)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.number = number;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        CustomGridViewAdapter2.RecordHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CustomGridViewAdapter2.RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            row.setTag(holder);
        }
        else
        {
            holder = (CustomGridViewAdapter2.RecordHolder) row.getTag();
        }

        Item2 item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.imageItem.setImageBitmap(item.getImage());

        return row;
    }

    public static class RecordHolder
    {
        public TextView txtTitle;
        public ImageView imageItem;

    }

    @Override
    public boolean isEnabled(int position) {
        if (position == number) {
            return false;
        } else {
            return true;
        }
    }


}

