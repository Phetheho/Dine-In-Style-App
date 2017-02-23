package com.example.codetribe.dineinstyle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Codetribe on 2017/01/23.
 */

public class ItemAdapter extends ArrayAdapter<Item> {


    public ItemAdapter(Activity context, ArrayList<Item> itemArrayList){

        super(context, 0 ,itemArrayList);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView; 

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent,false);
        }


        Item currentItem = getItem(position);


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.item_name);

        nameTextView.setText(currentItem.getItemName());


        TextView priceTextView = (TextView) listItemView.findViewById(R.id.item_price);

        priceTextView.setText(Double.toString(currentItem.getItemPrice()));


        return listItemView;


    }

}
