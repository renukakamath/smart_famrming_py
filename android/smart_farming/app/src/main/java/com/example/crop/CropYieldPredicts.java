package com.example.crop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class CropYieldPredicts extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    String years[]={"Kharif","Whole Year","Autumn","Rabi","Summer","Winter"};
    Spinner s1,s2,s3,s4;
    Button b1;
    String[] sid,state,pid,place,cid,crops;
    public static String sids,pids,cids,yy;
    TextView t1;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_yield_predicts);

        e1=(EditText)findViewById(R.id.etacre) ;
        s1=(Spinner)findViewById(R.id.spinner);
        t1=(TextView)findViewById(R.id.textView4);
        s1.setOnItemSelectedListener(this);
        s2=(Spinner)findViewById(R.id.spinner1);
        s2.setOnItemSelectedListener(this);
        s3=(Spinner)findViewById(R.id.spinner2);
        s3.setOnItemSelectedListener(this);
        ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, years);
        s3.setAdapter(ar);
        s4=(Spinner)findViewById(R.id.spinner3);
        s4.setOnItemSelectedListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) CropYieldPredicts.this;
        String q = "/viewstate";
        q = q.replace(" ", "%20");
        JR.execute(q);

        JsonReq JR1 = new JsonReq();
        JR1.json_response = (JsonResponse) CropYieldPredicts.this;
        String q1 = "/viewcrop";
        q1 = q1.replace(" ", "%20");
        JR1.execute(q1);



        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) CropYieldPredicts.this;
                String q = "/predcityield?sid="+sids+"&pid="+pids+"&yy="+yy+"&cid="+cids+"&acre="+e1.getText().toString();
                q = q.replace(" ", "%20");
                JR.execute(q);


            }
        });

    }

    @Override
    public void response(JSONObject jo) {


        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("viewstate")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    sid = new String[ja1.length()];
                    state = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {


                        sid[i] = ja1.getJSONObject(i).getString("state_id");
                        state[i] = ja1.getJSONObject(i).getString("state");


//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
//                        val[i] = "\nfirst_name:" + fname[i] + "\nlast_name:" + lname[i] + "\nplace:" + place[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, state);
                    s1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            else if (method.equalsIgnoreCase("viewplace")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    pid = new String[ja1.length()];
                    place = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {


                        pid[i] = ja1.getJSONObject(i).getString("place_id");
                        place[i] = ja1.getJSONObject(i).getString("place");


//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
//                        val[i] = "\nfirst_name:" + fname[i] + "\nlast_name:" + lname[i] + "\nplace:" + place[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, place);
                    s2.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            else if (method.equalsIgnoreCase("viewcrop")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    cid = new String[ja1.length()];
                    crops = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {


                        cid[i] = ja1.getJSONObject(i).getString("crop_id");
                        crops[i] = ja1.getJSONObject(i).getString("crop_name");


//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
//                        val[i] = "\nfirst_name:" + fname[i] + "\nlast_name:" + lname[i] + "\nplace:" + place[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, crops);
                    s4.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

            if (method.equalsIgnoreCase("predcityield")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    t1.setText("Yield Predcited is : "+jo.getString("result"));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed!!!", Toast.LENGTH_LONG).show();
                }
            }



        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spinner) {
//            Toast.makeText(getApplicationContext(), view + "", Toast.LENGTH_LONG).show();
            sids=sid[position];
            JsonReq JR = new JsonReq();
            JR.json_response = (JsonResponse) CropYieldPredicts.this;
            String q = "/viewplace?sid="+sids;
            q = q.replace(" ", "%20");
            JR.execute(q);
        }
        if(parent.getId()==R.id.spinner1) {
            Toast.makeText(getApplicationContext(), "Haiiiiiiii" + "", Toast.LENGTH_LONG).show();
            pids=pid[position];
        }
        if(parent.getId()==R.id.spinner2) {
            if(years[position].equalsIgnoreCase("Kharif"))
            {
                yy="1";
            }
            if(years[position].equalsIgnoreCase("Whole Year"))
            {
                yy="2";
            }
            if(years[position].equalsIgnoreCase("Autumn"))
            {
                yy="3";
            }
            if(years[position].equalsIgnoreCase("Rabi"))
            {
                yy="4";
            }
            if(years[position].equalsIgnoreCase("Summer"))
            {
                yy="5";
            }
            if(years[position].equalsIgnoreCase("Winter"))
            {
                yy="6";
            }
//            yy=pid[position];
        }
        if(parent.getId()==R.id.spinner3) {
            cids=cid[position];
        }
//
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}