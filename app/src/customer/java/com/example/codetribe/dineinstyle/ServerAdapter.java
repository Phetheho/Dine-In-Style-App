package com.example.codetribe.dineinstyle;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Codetribe on 2016/11/25.
 */
public class ServerAdapter extends ArrayAdapter<Server> {


    public ServerAdapter(Activity context, ArrayList<Server> serverArrayList){

        super(context, 0 ,serverArrayList);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.servers_list,parent,false);
        }


        Server currentServer = getItem(position);


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.server_name);

        nameTextView.setText(currentServer.getmServerName());


        TextView ageTextView = (TextView) listItemView.findViewById(R.id.server_age);

        ageTextView.setText(currentServer.getmServerAge());


        TextView experienceTextView = (TextView) listItemView.findViewById(R.id.server_experience);

        experienceTextView.setText(currentServer.getmServerExperience());


        ImageView imageView = (ImageView) listItemView.findViewById(R.id.server_image_view);


        if (currentServer.hasImage()){
            imageView.setImageResource(currentServer.getmImageResourceId());

            imageView.setVisibility(View.VISIBLE);

        }
        else{
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
