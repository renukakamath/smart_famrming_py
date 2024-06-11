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

public class ViewTutorials extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] id,title,des,path,type,value,fname,lname,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutorials);
        l1=(ListView) findViewById(R.id.viewtu);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewTutorials.this;
        String q = "/viewtu";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                 id= new String[ja1.length()];
                 title= new String[ja1.length()];
                des = new String[ja1.length()];
                path = new String[ja1.length()];
                type = new String[ja1.length()];
                fname = new String[ja1.length()];
                lname= new String[ja1.length()];
                phone= new String[ja1.length()];
                email= new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    id[i] = ja1.getJSONObject(i).getString("officer_id");
                    title[i] = ja1.getJSONObject(i).getString("title");
                    des[i] = ja1.getJSONObject(i).getString("description");
                    path[i] = ja1.getJSONObject(i).getString("file_path");
                    type[i] = ja1.getJSONObject(i).getString("file_type");
                    fname[i] = ja1.getJSONObject(i).getString("first_name");
                    lname[i] = ja1.getJSONObject(i).getString("last_name");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    value[i] = "Officer Id : " + id[i] + "\nTitle : " +title[i]+"\nDescription" +des[i]+"\nPath : "+path[i]+"\nType  : "+type[i];
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);

                Custimage2 a = new Custimage2(this, fname, lname, phone, email,title,des,path,type);
                l1.setAdapter(a);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),FarmerHome.class);
        startActivity(b);
    }
}