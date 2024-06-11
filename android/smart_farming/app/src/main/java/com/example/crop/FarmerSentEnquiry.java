package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class FarmerSentEnquiry extends AppCompatActivity implements JsonResponse {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    ListView l1;
    String enq;
    String[] enquiry,reply,date,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_sent_enquiry);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1 = findViewById(R.id.button4);
        e1=findViewById(R.id.editTextTextPersonName5);
        l1=findViewById(R.id.lvenq);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) FarmerSentEnquiry.this;
        String q = "/viewenq?log_id=" + sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enq = e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) FarmerSentEnquiry.this;
                String q = "/enqsent?enq=" + enq + "&log_id=" + sh.getString("log_id","");
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("enqsent")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Enquiry sent successfully ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), FarmerSentEnquiry.class));

                }


            }
            else if (method.equalsIgnoreCase("viewenq")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    enquiry = new String[ja1.length()];
                    reply = new String[ja1.length()];
                    date = new String[ja1.length()];
                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        enquiry[i] = ja1.getJSONObject(i).getString("description");
                        reply[i] = ja1.getJSONObject(i).getString("reply");
                        date[i] = ja1.getJSONObject(i).getString("date");

                        value[i] = "Enquiry : " + enquiry[i] + "\nReply : " + reply[i] + "\nDate : " + date[i]+"\n";
                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }
        }
        catch(Exception e){
                // TODO: handle exception

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