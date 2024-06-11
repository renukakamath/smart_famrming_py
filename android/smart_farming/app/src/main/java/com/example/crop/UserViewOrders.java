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

public class UserViewOrders extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] name,des,value,pid,image,amount,quantity,date,stat,total_amount;
    public static String price,om,stt,total_amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_orders);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.listor);
        l1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)UserViewOrders.this;
        String q="/myorders?log_id="+sh.getString("log_id","");
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        price=amount[i];
        total_amounts = total_amount[i];
        om=pid[i];
        stt=stat[i];


            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(UserViewOrders.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {



                        if (items[item].equals("Make Payment")) {

                            if (stt.equalsIgnoreCase("pending")) {
//                                Toast.makeText(getApplicationContext(), "Verification Not Completed", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(), UserPayment.class));
                            }else {

                                if (stt.equalsIgnoreCase("Payment completed") || stt.equalsIgnoreCase("Delivery Completed")) {
                                    Toast.makeText(getApplicationContext(), "Payment Already completed", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), UserViewOrders.class));
                                }else {
                                    startActivity(new Intent(getApplicationContext(), UserPayment.class));

                                }
                        }
                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
//        }else {
//
//
//        final CharSequence[] items = {"Make Payment", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewOrders.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("Make Payment")) {
//
//                    startActivity(new Intent(getApplicationContext(),UserPayment.class));}
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//
//            }
//
//        });
//        builder.show();
//        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),UserHome.class);
        startActivity(b);
    }
}