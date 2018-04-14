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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, DialogInterface.OnClickListener {
    private EditText nameBox, descBox, catBox, atBox, asBox, elevBox, latBox, lonBox;
    private ListView placesLV;
    private PlaceLibrary places;
    private String[] placeNames;

    private String[] colLabels;
    private int[] colIds;
    private List<HashMap<String,String>> fillMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placesLV = (ListView) findViewById(R.id.places_list_view);
        Intent intent = getIntent();
        places = intent.getSerializableExtra("places")!=null ? (PlaceLibrary) intent.getSerializableExtra("places") :
                new PlaceLibrary(this);
        placeNames = places.getNames();
        Log.d(getClass().getSimpleName(), "onCreate()" + placeNames[0]);
        this.prepareAdapter();
        SimpleAdapter sa = new SimpleAdapter(this, fillMaps, R.layout.place_list_item, colLabels, colIds);
        placesLV.setAdapter(sa);
        placesLV.setOnItemClickListener(this);

        setTitle("Places");
    }

    private void prepareAdapter() {
        colLabels = this.getResources().getStringArray(R.array.col_header);
        colIds = new int[]{R.id.name};
        this.placeNames = places.getNames();
        Arrays.sort(this.placeNames);
        Log.d(getClass().getSimpleName(), "prepareAdapter()" + placeNames[0]);
        fillMaps = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> titles = new HashMap<>();
        // first row contains column headers
        titles.put("Place Name", "Place Name");
        fillMaps.add(titles);
        for (int i = 0; i < placeNames.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Place Name", placeNames[i]);
            fillMaps.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                this.newPlaceAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        String[] placesNames = places.getNames();
        Arrays.sort(placesNames);
        if(position > 0 && position <= placesNames.length) {
            Intent displayPlace = new Intent(this, PlaceDetailActivity.class);
            displayPlace.putExtra("places", places);
            displayPlace.putExtra("selected", placesNames[position-1]);

            android.util.Log.d(this.getClass().getSimpleName(),"1");
            this.startActivityForResult(displayPlace, 1);
            android.util.Log.d(this.getClass().getSimpleName(),"2");
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        places = data.getSerializableExtra("places")!=null ? (PlaceLibrary) data.getSerializableExtra("places") : places;
        this.prepareAdapter();
        Log.d(getClass().getSimpleName(), "onActivityResult()" + placeNames[0]);
        SimpleAdapter sa = new SimpleAdapter(this, fillMaps, R.layout.place_list_item, colLabels, colIds);
        placesLV.setAdapter(sa);
        placesLV.setOnItemClickListener(this);
    }

    private void newPlaceAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enter Place Detail");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        this.nameBox = new EditText(this);
        nameBox.setHint("Name");
        layout.addView(nameBox);

        this.descBox = new EditText(this);
        descBox.setHint("Description");
        layout.addView(descBox);

        this.catBox = new EditText(this);
        catBox.setHint("Category");
        layout.addView(catBox);

        this.atBox = new EditText(this);
        atBox.setHint("Address Title");
        layout.addView(atBox);

        this.asBox = new EditText(this);
        asBox.setHint("Address Street");
        layout.addView(asBox);

        this.elevBox = new EditText(this);
        elevBox.setHint("Elevation");
        elevBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(elevBox);

        this.latBox = new EditText(this);
        latBox.setHint("Latitude");
        latBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(latBox);

        this.lonBox = new EditText(this);
        lonBox.setHint("Longitude");
        lonBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(lonBox);

        dialog.setView(layout);
        dialog.setNegativeButton("Cancel", this);
        dialog.setPositiveButton("Add", this);
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int whichButton) {
        android.util.Log.d(this.getClass().getSimpleName(),"onClick positive button? "+
                (whichButton==DialogInterface.BUTTON_POSITIVE));
        if(whichButton == DialogInterface.BUTTON_POSITIVE) {
            String name, desc, cat, at, as;
            Double elev, lat, lon;

            if(nameBox.getText().toString().matches(""))
                name = "";
            else
                name = nameBox.getText().toString();
            if(descBox.getText().toString().matches(""))
                desc = "";
            else
                desc = descBox.getText().toString();
            if(catBox.getText().toString().matches(""))
                cat = "";
            else
                cat = catBox.getText().toString();
            if(atBox.getText().toString().matches(""))
                at = "";
            else
                at = atBox.getText().toString();
            if(asBox.getText().toString().matches(""))
                as = "";
            else
                as = asBox.getText().toString();
            if(elevBox.getText().toString().matches(""))
                elev = 0.0;
            else
                elev = Double.parseDouble(elevBox.getText().toString());
            if(latBox.getText().toString().matches(""))
                lat = 0.0;
            else
                lat = Double.parseDouble(latBox.getText().toString());
            if(lonBox.getText().toString().matches(""))
                lon = 0.0;
            else
                lon = Double.parseDouble(lonBox.getText().toString());

            places.add(new PlaceDescription(name,desc,cat,at,as,elev,lat,lon));
            prepareAdapter();
            SimpleAdapter sa = new SimpleAdapter(this, fillMaps, R.layout.place_list_item, colLabels, colIds);
            placesLV.setAdapter(sa);
        }
    }
}
