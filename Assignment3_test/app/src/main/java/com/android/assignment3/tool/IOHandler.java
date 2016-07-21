package com.android.assignment3.tool;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.android.assignment3.R;
import com.android.assignment3.bangball.Game;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/20.
 */
public class IOHandler {
    Context context;
    public IOHandler(Context context){
        this.context = context;
    }

    public void initializeLevel(String fileName){
        File file = new File(context.getFilesDir(),fileName);
        try {
            if (!file.exists()){
                file.createNewFile();
                writeFile(fileName, Game.GlobalVar.resources.getInteger(R.integer.initial_level_num));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String readFile(String fileName){
        StringBuffer sb = new StringBuffer();
        String string;
        try {
            File file = new File(context.getFilesDir(), fileName);
            if (!file.exists())
                file.createNewFile();
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((string = reader.readLine()) != null){
                sb.append(string);
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void writeFile(String fileName,String key,int startNum){
        String level = readFile(fileName);
        JSONObject jo;
        try {
            if (level.length()>0){
                jo = new JSONObject(level);
            }else {
                jo = new JSONObject();
            }
            if (jo.getInt(key)<startNum)
                jo.put(key,startNum);
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(jo.toString().getBytes());
            fos.close();
        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeFile(String fileName,int num){
        String level = readFile(fileName);
        JSONObject jo;
        try {
            if (level.length()>0){
                jo = new JSONObject(level);
            }else {
                jo = new JSONObject();
            }
            for (int i=1;i<num+1;i++){
                String key = String.valueOf(i);
                jo.put(key,-1);
            }
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(jo.toString().getBytes());
            fos.close();
        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public JSONObject readJSON(String fileName){
        StringBuffer sb = new StringBuffer();
        String string;
        JSONObject jsonObject = new JSONObject();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((string = reader.readLine()) != null){
                sb.append(string);
            }
            jsonObject = new JSONObject(sb.toString());
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
