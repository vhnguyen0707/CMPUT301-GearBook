package com.example.nhvu_gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddGearFragment.OnFragmentInteractionListener {
    // Variables declaration for reference later
    private ListView gearList;
    private ArrayAdapter<Gear> gearAdapter;
    private List<Gear> gearDataList;
    private FloatingActionButton addGearButton;
    float totalPrice;
    TextView totalPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gearList = findViewById(R.id.gear_list);
        gearDataList = new ArrayList<>();
        gearAdapter = new CustomList(this, gearDataList);
        gearList.setAdapter(gearAdapter);
        totalPriceView = findViewById(R.id.total_price_textView);
        updateTotalPrice();

        addGearButton = findViewById(R.id.add_gear_button);
        addGearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new AddGearFragment().show(getSupportFragmentManager(),"ADD_GEAR");
                updateTotalPrice();
            }
        });

        gearList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gear clickedGear = gearDataList.get(position);
                AddGearFragment.newInstance(clickedGear, position).show(getSupportFragmentManager(),"EDIT_GEAR");
                updateTotalPrice();
            }
        });
    }

    @Override
    public void onGearAdded(Gear newGear){
        gearAdapter.add(newGear);
        updateTotalPrice();
    }

    @Override
    public void onGearEdited(Gear editedGear, int index){
        gearDataList.set(index, editedGear);
        gearAdapter.notifyDataSetChanged();
        updateTotalPrice();

    }

    @Override
    public void onGearDeleted(int index){
        //checks size of gearList and enables Delete when the list is not empty
        if (gearList.getChildCount() > 0) {
            gearDataList.remove(index);
            gearAdapter.notifyDataSetChanged();
            updateTotalPrice();
        }
    }

    public void updateTotalPrice(){
        totalPrice = 0;
        for (int i = 0; i < gearDataList.size(); i++){
            totalPrice += Float.parseFloat(gearDataList.get(i).getPrice());
        }
        //displays 2 digits after the decimal point.
        String total = String.format("%.02f",totalPrice);
        totalPriceView.setText("Total: CAD"+ total);
    }

}
