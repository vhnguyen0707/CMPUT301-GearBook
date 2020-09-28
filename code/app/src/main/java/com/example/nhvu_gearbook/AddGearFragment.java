package com.example.nhvu_gearbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddGearFragment extends DialogFragment {
    /*----------------------------------
    This class is used to add/edit/delete/
    view information of a gear when it is
    selected in ListView. It will alert if
    the fields are not filled in correctly
   -------------------------------------*/
    private EditText date;
    private EditText maker;
    private EditText description;
    private EditText price;
    private EditText comment;

    private OnFragmentInteractionListener listener;
    private Gear currentGear;
    private int index;
    private String dialog;

    private boolean boolDate = false;
    private boolean boolMaker = false;
    private boolean boolDescription = false;
    private boolean boolPrice = false;

    public interface OnFragmentInteractionListener{
        void onGearAdded(Gear newGear);
        void onGearEdited(Gear editedGear, int index);
        void onGearDeleted(int index);
    }

    static AddGearFragment newInstance(Gear gear, int index){
        Bundle args = new Bundle();
        args.putSerializable("gear", gear);
        args.putSerializable("index", index);

        AddGearFragment fragment = new AddGearFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //Input checking
    public boolean date_check(String date){
        //Check if the string is in right format for date yyyy-mm-dd
        //Set preferred date format
        if (date.isEmpty()){return false;}
        else{ SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
            } catch (ParseException e){
                return false;
            }
            return true;
        }
    }

    public boolean price_check(String price){
    //Checks if the string can be in a positive float form
        if (price.isEmpty()) {return false;}
        else{
            try {
            Float.parseFloat(price);
            } catch(NumberFormatException | NullPointerException nfe){
                return false;
            }
            if (Float.parseFloat(price)<0) {return false;};
            return true;

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        //Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.gear_fragment_layout, null);


        /*----------------------------------------------------------------------------------------------
         Instantiate TextWatcher to monitor inputs
         Boolean values are set to false for empty fields at first
         Check if inputs are in correct formats
         (Warnings keep displaying until valid inputs are in
         If passes, set Error to null and boolean values to true
         reference: https://stackoverflow.com/questions/50657197/textwatcher-monitor-only-current-input
         ---------------------------------------------------------------------------------------------*/


        date = view.findViewById(R.id.date_editText);
        date.setError("Field cannot be empty");
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (date.getText().toString().isEmpty()){
                    date.setError("Field cannot be empty");
                }else if(!date_check(date.getText().toString())){
                    date.setError("Please enter a valid date");
                } else {
                    date.setError(null);
                    boolDate = true;
                }
            }
        });

        maker = view.findViewById(R.id.maker_editText);
        maker.setError("Field cannot be empty");
        maker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (maker.getText().toString().isEmpty()){
                    maker.setError("Field cannot be empty");
                } else {
                    maker.setError(null);
                    boolMaker = true;
                }
            }
        });
        description = view.findViewById(R.id.description_editText);
        description.setError("Field cannot be empty");
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (description.getText().toString().isEmpty()){
                    description.setError("Field cannot be empty");
                } else {
                    description.setError(null);
                    boolDescription = true;
                }
            }
        });
        price = view.findViewById(R.id.price_editText);
        price.setError("Field cannot be empty");
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (price.getText().toString().isEmpty()){
                    price.setError("Field cannot be empty");
                }else if(!price_check(price.getText().toString())){
                    price.setError("Please enter a valid price");
                } else {
                    price.setError(null);
                    boolPrice = true;
                }
            }
        });
        comment = view.findViewById(R.id.comment_editText);

        //If the fragment is used to edit or delete an existing entry, show the information.
        Bundle args = getArguments();
        if (args != null){
            dialog = "EDIT/ DELETE GEAR";
            currentGear = (Gear) args.getSerializable("gear");
            index = args.getInt("index");

            date.setText(currentGear.getDate());
            maker.setText(currentGear.getMaker());
            description.setText(currentGear.getDescription());
            price.setText(currentGear.getPrice());
            comment.setText(currentGear.getComment());
        } else {
            dialog = "ADD NEW GEAR";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle(dialog)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(index);
                        listener.onGearDeleted(index);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gearDate = date.getText().toString();
                        String gearMaker = maker.getText().toString();
                        String gearDescription = description.getText().toString();
                        String gearPrice = price.getText().toString();
                        String gearComment = comment.getText().toString();

                        //Check if the input entries are in the valid formats and not empty
                        if ((boolDate)&&(boolMaker)&&(boolDescription)&&(boolPrice)){
                            Gear gear = new Gear(gearDate, gearMaker, gearDescription, gearPrice, gearComment);
                            //If the Gear for the fragment is not null, we are editing an existing gear
                            //Otherwise, we must be adding a new gear.
                            if(currentGear != null) {
                                listener.onGearEdited(gear, index);
                                Toast toast = Toast.makeText(getContext(),"Selected Entry successfully edited!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0,0);
                                toast.show();

                            } else {
                                listener.onGearAdded(gear);
                                Toast toast = Toast.makeText(getContext(),"New Entry successfully added!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(getContext(),"Some of your inputs are in wrong format! Please try again!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }
                    }
                }).create();
    }




}
