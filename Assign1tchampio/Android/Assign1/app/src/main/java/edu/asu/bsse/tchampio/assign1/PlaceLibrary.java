package edu.asu.bsse.tchampio.assign1;

import android.app.Activity;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

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
 * Purpose: Holds information about a Place passed in by JSON data.
 *
 * @author Terin Champion terinchampion@gmail.com
 * @version March, 2018
 */

public class PlaceLibrary extends Object implements Serializable{

    public Hashtable<String, PlaceDescription> places;

    public PlaceLibrary(Activity parent){
        places = new Hashtable<String, PlaceDescription>();
        try {
            this.resetFromJsonFile(parent);
        } catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "error resetting from places json file" + ex.getMessage());
        }
    }

    public boolean resetFromJsonFile(Activity parent) {
        boolean ret = true;
        try {
            places.clear();
            InputStream is = parent.getApplicationContext().getResources().openRawResource(R.raw.places);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                sb.append(br.readLine());
            }
            String placesJsonStr = sb.toString();
            JSONObject placesJson = new JSONObject(new JSONTokener(placesJsonStr));
            Iterator<String> it = placesJson.keys();
            while (it.hasNext()) {
                String placeName = it.next();
                JSONObject aPlace = placesJson.optJSONObject(placeName);
                if (aPlace != null) {
                    PlaceDescription place = new PlaceDescription(aPlace.toString());
                    places.put(placeName, place);
                }
            }
        } catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "Exception reading json file: " + ex.getMessage());
            ret = false;
        }
        return ret;
    }

    public boolean add(PlaceDescription place) {
        boolean ret = true;
        try {
            places.put(place.getName(), place);
        } catch (Exception ex) {
            ret = false;
        }
        return ret;
    }

    public boolean remove(String placeName) {
        return ((places.remove(placeName) == null) ? false : true);
    }

    public String[] getNames() {
        String[] ret = {};
        if (places.size() > 0) {
            ret = (String[]) (places.keySet()).toArray(new String[0]);
        }
        return ret;
    }

    public PlaceDescription get(String placeName) {
        PlaceDescription place = null;
        place = places.get(placeName);
        return place;
    }
}
