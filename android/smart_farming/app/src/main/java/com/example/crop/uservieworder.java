package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class uservieworder extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] name,des,value,pid,image,amount,quantity,date,stat,total_amount;
    public static String price,om,stt,total_amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uservieworder);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.listors);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)uservieworder.this;
        String q="/userviewmyorders?log_id="+sh.getString("log_id","");
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
                name = new String[ja1.length()];
                des = new String[ja1.length()];
                pid = new String[ja1.length()];
                value = new String[ja1.length()];
                image = new String[ja1.length()];
                amount = new String[ja1.length()];
                quantity = new String[ja1.length()];
                date = new String[ja1.length()];
                stat = new String[ja1.length()];
                total_amount = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("crop_name");
                    des[i] = ja1.getJSONObject(i).getString("description");
                    pid[i] = ja1.getJSONObject(i).getString("om_id");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    stat[i] = ja1.getJSONObject(i).getString("oder_status");
                    total_amount[i] = ja1.getJSONObject(i).getString("total_amount");

                    value[i] = "Crop Name : " + name[i] + "\nDescription : " + des[i]+"\nAmount :"+amount[i]+"\ndate : "+date[i]+"\nStatus : "+stat[i]+"\n";
                }

            }
            ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
            l1.setAdapter(ar);
//            Custimage a = new Custimage(this, name, des, image, amount,quantity);
//            l1.setAdapter(a);

        }

        catch(
                Exception e)

        {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}