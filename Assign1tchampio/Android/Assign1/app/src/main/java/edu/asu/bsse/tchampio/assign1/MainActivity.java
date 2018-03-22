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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public TextView nameTV, descTV, catTV, addTTV, addSTV, elevTV, latTV, lonTV;
    public PlaceDescription place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        place = getPlace();
        setView();
    }

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
