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
 * Purpose: Holds information about a Place passed in by JSON data.
 *
 * @author Terin Champion terinchampion@gmail.com
 * @version March, 2018
 */
import org.json.JSONObject;

import java.io.Serializable;

public class PlaceDescription implements Serializable{
    private String name, description, category, addressTitle, addressStreet;
    private Double elevation, latitude, longitude;

    public PlaceDescription(String name, String description, String category, String addressTitle, String addressStreet, Double elevation, Double latitude, Double longitude){
        this.name = name;
        this.description = description;
        this.category = category;
        this.addressTitle = addressTitle;
        this.addressStreet = addressStreet;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public PlaceDescription(String jsonStr){
        try{
            JSONObject jo = new JSONObject(jsonStr);
            name = jo.getString("name");
            description = jo.getString("description");
            category = jo.getString("category");
            addressTitle = jo.getString("address-title");
            addressStreet = jo.getString("address-street");
            elevation = jo.getDouble("elevation");
            latitude = jo.getDouble("latitude");
            longitude = jo.getDouble("longitude");


        }
        catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),"error converting from json");
        }
    }

    public String toJsonString(){
        String ret = "";
        try{
            JSONObject jo = new JSONObject();
            jo.put("name",name);
            jo.put("description", description);
            jo.put("category", category);
            jo.put("address-title", addressTitle);
            jo.put("address-street", addressStreet);
            jo.put("elevation", elevation);
            jo.put("latitude", latitude);
            jo.put("longitude", longitude);

            ret = jo.toString();
        }
        catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),"error converting to json");
        }
        return ret;
    }

    public JSONObject toJson(){
        JSONObject jo = new JSONObject();
        try{
            jo.put("name",name);
            jo.put("description", description);
            jo.put("category", category);
            jo.put("address-title", addressTitle);
            jo.put("address-street", addressStreet);
            jo.put("elevation", elevation);
            jo.put("latitude", latitude);
            jo.put("longitude", longitude);

        }
        catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(),"error converting to json");
        }
        return jo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
