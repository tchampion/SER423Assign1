package edu.asu.bsse.tchampio.assign1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
 * @version April, 2018
 */

public class PlaceDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String dbName = "placedb";
    private String dbPath;
    private SQLiteDatabase plDB;
    private final Context context;

    public PlaceDB(Context context){
        super(context, dbName, null, DATABASE_VERSION);
        this.context = context;
        dbPath = context.getFilesDir().getPath()+"/";
    }

    public void createDB() throws IOException{
        this.getReadableDatabase();
        try{
            copyDB();
        } catch (IOException e) {
            android.util.Log.w(this.getClass().getSimpleName(), "Error created DB " + e.getMessage());
        }
    }

    private boolean checkDB(){
        SQLiteDatabase checkDB = null;
        boolean exists = false;
        try{
            String path = dbPath + dbName + ".db";
            File file = new File(path);
            if(file.exists()){
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

            }
        }catch(SQLiteException e){
        android.util.Log.w("placeDB->checkDB",e.getMessage());
    }
        if(checkDB != null){
        checkDB.close();
        exists = true;
    }
        return exists;
    }

    public void copyDB() throws IOException{
        try {
            if(!checkDB()){
                InputStream ip =  context.getResources().openRawResource(R.raw.placedb);
                // make sure the database path exists. if not, create it.
                File aFile = new File(dbPath);
                if(!aFile.exists()){
                    aFile.mkdirs();
                }
                String op=  dbPath  +  dbName +".db";
                OutputStream output = new FileOutputStream(op);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = ip.read(buffer))>0){
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                ip.close();
            }
        } catch (IOException e) {
            android.util.Log.w("CourseDB --> copyDB", "IOException: "+e.getMessage());
        }
    }

    public SQLiteDatabase openDB() throws SQLException {
        String myPath = dbPath + dbName + ".db";
        if(checkDB()) {
            plDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }else{
            try {
                this.copyDB();
                plDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            }catch(Exception ex) {
                android.util.Log.w(this.getClass().getSimpleName(),"unable to copy and open db: "+ex.getMessage());
            }
        }
        return plDB;
    }

    @Override
    public synchronized void close() {
        if(plDB != null)
            plDB.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
