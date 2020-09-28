package com.example.nhvu_gearbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;

public class CustomList extends ArrayAdapter<Gear> {
    ///CustomList displays gears to the custom ListView
    private List<Gear> gears;
    private Context context;

    public CustomList(Context context, List<Gear> gears){
        super(context, 0, gears);
        this.gears = gears;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //inflate tha layout for each row of the list
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent, false);
        }
        //get current item to be displayed
        Gear gear = gears.get(position);
        //get the TextView for gear's date, description and price
        TextView gearDate = view.findViewById(R.id.gear_date);
        TextView gearDescription = view.findViewById(R.id.gear_description);
        TextView gearPrice = view.findViewById(R.id.gear_price);
        //set the text for gear's date, description and price from the current gear object
        gearDate.setText(gear.getDate());
        gearDescription.setText(gear.getDescription());
        gearPrice.setText("CA$ "+ gear.getPrice());
        //returns view for the current row
        return view;
    }
}
