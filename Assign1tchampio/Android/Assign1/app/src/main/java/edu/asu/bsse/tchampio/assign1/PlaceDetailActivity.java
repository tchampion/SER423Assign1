package edu.asu.bsse.tchampio.assign1;
/**
 * Copyright 2018 Terin Champion,
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Purpose: Displays information about a PlaceDescription.
 *
 * @author Terin Champion terinchampion@gmail.com
 * @version March, 2018
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
                                                                        DialogInterface.OnClickListener{

    public TextView nameTV, descTV, catTV, addTTV, addSTV, elevTV, latTV, lonTV, nameBox, gcTV, bearingTV;
    private EditText descBox, catBox, atBox, asBox, elevBox, latBox, lonBox;
    private PlaceLibrary places;
    private String selectedPlace, spinnerPlace;
    private String[] availPlaces;
    private Spinner placeSpinner;
    private PlaceDescription place;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.util.Log.d(this.getClass().getSimpleName(),"3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        places = intent.getSerializableExtra("places")!=null ? (PlaceLibrary) intent.getSerializableExtra("places") :
                new PlaceLibrary(this);
        selectedPlace = intent.getStringExtra("selected")!=null ? intent.getStringExtra("selected") : "unknown";
        place = places.get(selectedPlace);
        Intent i = new Intent();
        i.removeExtra("places");
        i.putExtra("places", places);
        this.setResult(RESULT_OK,i);
        placeSpinner = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();
        availPlaces = places.getNames();
        for(String ap : availPlaces)
            list.add(ap);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(adapter);
        placeSpinner.setOnItemSelectedListener(this);

        setView();

    }

    @Override
    public void onBackPressed(){

        Intent i = new Intent();
        i.removeExtra("places");
        i.putExtra("places", places);
        this.setResult(RESULT_OK,i);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.place_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                this.editPlaceAlert();
                return true;
            case R.id.action_delete:
                places.remove(this.selectedPlace);
                Intent i = new Intent();
                i.removeExtra("places");
                i.putExtra("places", places);
                this.setResult(RESULT_OK,i);
                finish();
                return true;
            case 16908332:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra("places", places);
                NavUtils.navigateUpTo(this,upIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.spinnerPlace = placeSpinner.getSelectedItem().toString();
        PlaceDescription place2 = places.get(spinnerPlace);
        Double lat1 = place.getLatitude();
        Double lon1 = place.getLongitude();
        Double lat2 = place2.getLatitude();
        Double lon2 = place2.getLongitude();

        Double x1 = Math.toRadians(lat1);
        Double y1 = Math.toRadians(lon1);
        Double x2 = Math.toRadians(lat2);
        Double y2 = Math.toRadians(lon2);

        Double angle1 = Math.acos(Math.sin(x1) * Math.sin(x2)
            + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 -y2));

        angle1 = Math.toDegrees(angle1);

        Double distance = (60 * angle1) * 1.15078;

        gcTV = (TextView) findViewById(R.id.greatCircleTV);
        String result = String.format("%.4f",distance);
        gcTV.setText(result + " Statute Miles");

        Double y = Math.sin(y2-y1) * Math.cos(x2);
        Double x = Math.cos(x1)*Math.sin(x2) - Math.sin(x1)*Math.cos(x2)*Math.cos(y2-y1);
        Double brng = Math.atan2(y,x);
        brng = Math.toDegrees(brng);


        bearingTV = (TextView) findViewById(R.id.bearingTV);
        result = String.format("%.4f",brng);
        bearingTV.setText(result + " Initial Bearing");

    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void editPlaceAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enter Place Detail");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        this.nameBox = new TextView(this);

        nameBox.setText(place.getName());
        layout.addView(nameBox);

        this.descBox = new EditText(this);
        descBox.setText(place.getDescription());
        layout.addView(descBox);

        this.catBox = new EditText(this);
        catBox.setText(place.getCategory());
        layout.addView(catBox);

        this.atBox = new EditText(this);
        atBox.setText(place.getAddressTitle());
        layout.addView(atBox);

        this.asBox = new EditText(this);
        asBox.setText(place.getAddressStreet());
        layout.addView(asBox);

        this.elevBox = new EditText(this);
        elevBox.setText(place.getElevation().toString());
        elevBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(elevBox);

        this.latBox = new EditText(this);
        latBox.setText(place.getLatitude().toString());
        latBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(latBox);

        this.lonBox = new EditText(this);
        lonBox.setText(place.getLongitude().toString());
        lonBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(lonBox);

        dialog.setView(layout);
        dialog.setNegativeButton("Cancel", this);
        dialog.setPositiveButton("Edit", this);
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int whichButton) {
        if(whichButton == DialogInterface.BUTTON_POSITIVE) {
            if(!descBox.getText().toString().matches(""))
                place.setDescription(descBox.getText().toString());
            else
                place.setDescription("");
            if(!catBox.getText().toString().matches(""))
                place.setCategory(catBox.getText().toString());
            else
                place.setDescription("");
            if(!atBox.getText().toString().matches(""))
                place.setAddressTitle(atBox.getText().toString());
            else
                place.setAddressTitle("");
            if(!asBox.getText().toString().matches(""))
                place.setAddressStreet(asBox.getText().toString());
            else
                place.setAddressStreet("");
            if(!elevBox.getText().toString().matches(""))
                place.setElevation(Double.parseDouble(elevBox.getText().toString()));
            else
                place.setElevation(0.0);
            if(!latBox.getText().toString().matches(""))
                place.setLatitude(Double.parseDouble(latBox.getText().toString()));
            else
                place.setLatitude(0.0);
            if(!lonBox.getText().toString().matches(""))
                place.setLongitude(Double.parseDouble(lonBox.getText().toString()));
            else
                place.setLongitude(0.0);

            Intent i = new Intent();
            i.removeExtra("places");
            i.putExtra("places", places);
            this.setResult(RESULT_OK,i);
            finish();
        }
    }
    /*
    public PlaceDescription getPlace(){
        String json;
        PlaceDescription p;
        try {

            InputStream is = getAssets().open("place.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer,"UTF-8");
            p  = new PlaceDescription(json);

            return p;
        }
        catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),"error opening json file");
        }
        return null;
    }
    */

    public void setView(){
        nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(place.getName());
        descTV = (TextView) findViewById(R.id.descTV);
        descTV.setText(place.getDescription());
        catTV = (TextView) findViewById(R.id.catTV);
        catTV.setText(place.getCategory());
        addTTV = (TextView) findViewById(R.id.addTTV);
        addTTV.setText(place.getAddressTitle());
        addSTV = (TextView) findViewById(R.id.addSTV);
        addSTV.setText(place.getAddressStreet());
        elevTV = (TextView) findViewById(R.id.elevTV);
        elevTV.setText(place.getElevation().toString());
        latTV = (TextView) findViewById(R.id.latTV);
        latTV.setText(place.getLatitude().toString());
        lonTV = (TextView) findViewById(R.id.lonTV);
        lonTV.setText(place.getLongitude().toString());
    }
}
