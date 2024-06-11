package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewNews extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] title, des, date, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        l1=(ListView) findViewById(R.id.newid);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewNews.this;
        String q = "/viewnews";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                title = new String[ja1.length()];
                des = new String[ja1.length()];
                date = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    title[i] = ja1.getJSONObject(i).getString("title");
                    des[i] = ja1.getJSONObject(i).getString("description");
                    date[i] = ja1.getJSONObject(i).getString("date");

                    value[i] = "Title : " + title[i] + "\nDescription : " + des[i] + "\nDate : " + date[i]+"\n";
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}