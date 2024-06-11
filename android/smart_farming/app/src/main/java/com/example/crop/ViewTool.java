package com.example.crop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewTool extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] product,amt,des,value,pid;
    String product_id,amount,status;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tool);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.toolid);
        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewTool.this;
        String q = "/viewtools";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("viewtools")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    product = new String[ja1.length()];
                    amt = new String[ja1.length()];
                    des = new String[ja1.length()];
                    value = new String[ja1.length()];
                    pid = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        product[i] = ja1.getJSONObject(i).getString("product");
                        amt[i] = ja1.getJSONObject(i).getString("amount");
                        des[i] = ja1.getJSONObject(i).getString("description");
                        pid[i] = ja1.getJSONObject(i).getString("product_id");


                        value[i] = "Product : " + product[i] + "\nAmount : " + amt[i] + "\nDescription : " + des[i]+"\n";
                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }
            if(method.equalsIgnoreCase("toolbooking")){

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Product booked Succesfully", Toast.LENGTH_LONG).show();

                }

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

        product_id=pid[i];

        amount=amt[i];
        final CharSequence[] items = {"Book now","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTool.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book now")) {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) ViewTool.this;
                    String q = "/toolbookings?product_id="+product_id+"&login_id="+sh.getString("log_id","")+"&amount="+amount;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

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