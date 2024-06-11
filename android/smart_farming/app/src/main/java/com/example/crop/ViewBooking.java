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

public class ViewBooking extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] product,amt,date,sts,des,value,bid;
    public static String booking_id,amount,stat;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.bookid);
        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewBooking.this;
        String q = "/viewbooking?fid="+sh.getString("log_id","");
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
                product = new String[ja1.length()];
                amt = new String[ja1.length()];
                date = new String[ja1.length()];
                sts = new String[ja1.length()];
                des= new String[ja1.length()];
                bid= new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    product[i] = ja1.getJSONObject(i).getString("product");
                    amt[i] = ja1.getJSONObject(i).getString("amount");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    sts[i] = ja1.getJSONObject(i).getString("status");
                    des[i] = ja1.getJSONObject(i).getString("description");
                    bid[i] = ja1.getJSONObject(i).getString("booking_id");

                    value[i] = "Product : " + product[i] + "\nDate : " + date[i]+"\nStatus : "+sts[i]+"\nDescription : "+ des[i]+"\n" ;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        booking_id=bid[i];
        amount=amt[i];
        stat=sts[i];

        if(stat.equals("Payment Completed")) {
            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBooking.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Make Payment")) {

                        Toast.makeText(getApplicationContext(), "Payment Already completed", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), ViewBooking.class));
                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }else {


        final CharSequence[] items = {"Make Payment", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBooking.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Make Payment")) {

                    startActivity(new Intent(getApplicationContext(),FarmerPayment.class));}

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
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