package com.example.crop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CropType extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    EditText e1;
    String[] name,des,value,pid,image,amount,quantity;
    public static String product_id,pname,desc,amt,qt,img;
    TextView t99;
    String search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_type);
        l1 = (ListView) findViewById(R.id.cropid);

        e1=(EditText)findViewById(R.id.search);
        l1.setOnItemClickListener(this);

        t99 = findViewById(R.id.res);
        t99.setVisibility(View.GONE);



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) CropType.this;
        String q = "/cropview";
        q = q.replace(" ", "%20");
        JR.execute(q);


        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) CropType.this;
                String q = "/searchmedicine?&search=" + search ;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                t99.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                name = new String[ja1.length()];
                des = new String[ja1.length()];
                pid = new String[ja1.length()];
                value = new String[ja1.length()];
                image = new String[ja1.length()];
                amount = new String[ja1.length()];
                quantity = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("crop_name");
                    des[i] = ja1.getJSONObject(i).getString("description");
                    pid[i] = ja1.getJSONObject(i).getString("crop_id");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    quantity[i] = ja1.getJSONObject(i).getString("availability");

                    value[i] = "Name : " + name[i] + "\nDescription : " + des[i];
                }

            }else{
                t99.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
            }
//            ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//            l1.setAdapter(ar);
            Custimage a = new Custimage(this, name, des, image, amount,quantity);
            l1.setAdapter(a);

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

        product_id=pid[i];
        pname=name[i];
        desc=des[i];
        amt=amount[i];
        qt=quantity[i];
        img=image[i];
        final CharSequence[] items = {"Book now","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CropType.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book now")) {

                startActivity(new Intent(getApplicationContext(),UserBooking.class));

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
        Intent b=new Intent(getApplicationContext(),UserHome.class);
        startActivity(b);
    }
}