package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class    FarmerVieworders extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] value,fname,lname,phone,email,cname,oid,avail,pincode,district,amount,st;
    public static String omid,fn,ln,ph,em,cn,quant,pin,dist,rate,stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_vieworders);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.viewod);
        l1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)FarmerVieworders.this;
        String q="/farmviewod?log_id="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("farmviewod")) {


                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    fname = new String[ja1.length()];
                    lname = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    value = new String[ja1.length()];
                    email = new String[ja1.length()];
                    cname = new String[ja1.length()];
                    oid = new String[ja1.length()];

                    avail = new String[ja1.length()];
                    pincode = new String[ja1.length()];
                    district = new String[ja1.length()];
                    amount = new String[ja1.length()];
                    st = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        fname[i] = ja1.getJSONObject(i).getString("first_name");
                        lname[i] = ja1.getJSONObject(i).getString("last_name");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        cname[i] = ja1.getJSONObject(i).getString("crop_name");
                        oid[i] = ja1.getJSONObject(i).getString("om_id");
                        st[i] = ja1.getJSONObject(i).getString("oder_status");
                        avail[i] = ja1.getJSONObject(i).getString("availability");
                        pincode[i] = ja1.getJSONObject(i).getString("pincode");
                        district[i] = ja1.getJSONObject(i).getString("district");
                        amount[i] = ja1.getJSONObject(i).getString("total_amount");


                        value[i] = "Crop Name : " + cname[i] + "\nUser : " + fname[i] + lname[i] + "\nPhone :" + phone[i] + "\nEmail : " + email[i]+ "\nOrder Status : " + st[i];
                    }

                }else if (status.equalsIgnoreCase("failed")) {
                    Toast.makeText(getApplicationContext(), "No Orders", Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
//            Custimage a = new Custimage(this, name, des, image, amount,quantity);
//            l1.setAdapter(a);

            }
            if (method.equalsIgnoreCase("farmacceptorder")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Verification Completed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),FarmerVieworders.class));
                }


            }
        }

        catch(
                Exception e)

        {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        omid=oid[i];
        fn=fname[i];
        ln=lname[i];
        ph=phone[i];
        em=email[i];
        cn=cname[i];
        quant=avail[i];
        pin=pincode[i];
        dist=district[i];
        rate=amount[i];
        stat=st[i];

        final CharSequence[] items = {"View Payment", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(FarmerVieworders.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Payment")) {
                        if(stat.equalsIgnoreCase("Payment completed") || stat.equalsIgnoreCase("Delivery Completed")){
                            startActivity(new Intent(getApplicationContext(),FarmerViewPayment.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "Payment Not Completed", Toast.LENGTH_LONG).show();
                        }
                    }

                else if (items[item].equals("Accept Order")) {

                    if(stat.equalsIgnoreCase("dispached")){
                        Toast.makeText(getApplicationContext(), " Already Dispached", Toast.LENGTH_LONG).show();
                    }
                    else if(stat.equalsIgnoreCase("pickup")){
                        Toast.makeText(getApplicationContext(), " Already Pick Up", Toast.LENGTH_LONG).show();
                    }
                    else if(stat.equalsIgnoreCase("delivered")){
                        Toast.makeText(getApplicationContext(), " Already Delivered", Toast.LENGTH_LONG).show();
                    }
                    else{


                        if (stat.equalsIgnoreCase("Verified by Farmer") || stat.equalsIgnoreCase("Payment completed")) {
                            Toast.makeText(getApplicationContext(), "Verification Already Completed", Toast.LENGTH_LONG).show();
                        } else {
                            JsonReq JR = new JsonReq();
                            JR.json_response = (JsonResponse) FarmerVieworders.this;
                            String q = "/farmacceptorder?om_id=" + omid;
                            q = q.replace(" ", "%20");
                            JR.execute(q);
                        }
                    }
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),FarmerHome.class);
        startActivity(b);
    }
}