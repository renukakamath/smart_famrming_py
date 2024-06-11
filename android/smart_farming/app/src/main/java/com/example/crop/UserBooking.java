package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserBooking extends AppCompatActivity implements JsonResponse {

    TextView t1,t2,t3,t4;
    EditText e1;
    Button b1;
    ImageButton i1;
    String quantity,avail;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        i1=(ImageButton) findViewById(R.id.imageButton);
        t1=(TextView) findViewById(R.id.pname);
        t2=(TextView) findViewById(R.id.pdesc);
        t3=(TextView) findViewById(R.id.pqty);
        t4=(TextView) findViewById(R.id.pam);

        e1=(EditText) findViewById(R.id.pquantity);

        b1=(Button) findViewById(R.id.pbtn);

        t1.setText("Product Name : "+CropType.pname);
        t2.setText("Description : "+CropType.desc);
        t3.setText("Available Quantity : "+CropType.qt);
        t4.setText("Rate : "+CropType.amt);
        avail=t3.getText().toString();

        String pth = "http://"+sh.getString("ip", "")+"/"+CropType.img;
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(getApplicationContext())
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(i1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = e1.getText().toString();

                if (quantity.equalsIgnoreCase("")){
                        e1.setError("Select Quantity");
                        e1.setFocusable(true);
                }else {

                    if(Integer.parseInt(CropType.qt) < Integer.parseInt(quantity)){
                        Toast.makeText(getApplicationContext(),"Available Quantity Exceeds !!!", Toast.LENGTH_LONG).show();
                    }else {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) UserBooking.this;
                        String q = "/userbooking?log_id=" + sh.getString("log_id", "") + "&quantity=" + quantity + "&pid=" + CropType.product_id;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                    }
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "bokking Successfull", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),UserHome.class));


            }
            if (status.equalsIgnoreCase("failed")) {

                Toast.makeText(getApplicationContext(), "bokking Failed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),UserHome.class));


            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),UserHome.class);
        startActivity(b);
    }
}