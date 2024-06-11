package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class FarmerViewPayment extends AppCompatActivity implements JsonResponse  {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_view_payment);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1=(TextView) findViewById(R.id.textView15);
        t2=(TextView) findViewById(R.id.textView16);
        t3=(TextView) findViewById(R.id.textView17);
        t4=(TextView) findViewById(R.id.textView18);
        t5=(TextView) findViewById(R.id.textView19);
        t6=(TextView) findViewById(R.id.textView20);
        t7=(TextView) findViewById(R.id.textView21);
        t8=(TextView) findViewById(R.id.textView22);
        Button b1=(Button) findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FarmerVieworders.stat.equalsIgnoreCase("dispatch")){
                    Toast.makeText(getApplicationContext(), "Dispatch  Completed", Toast.LENGTH_LONG).show();
                }
                else if(FarmerVieworders.stat.equalsIgnoreCase("pickup")){
                    Toast.makeText(getApplicationContext(), "pickup  Completed", Toast.LENGTH_LONG).show();
                }
                else if(FarmerVieworders.stat.equalsIgnoreCase("delivered")){
                    Toast.makeText(getApplicationContext(), "delivery  Completed", Toast.LENGTH_LONG).show();
                }
                else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) FarmerViewPayment.this;
                    String q = "/deliverysuccess?oid=" + FarmerVieworders.omid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });



        t1.setText("Customer Name : "+FarmerVieworders.fn+FarmerVieworders.ln);
        t2.setText("Customer phone : "+FarmerVieworders.ph);
        t3.setText("Customer Email : "+FarmerVieworders.em);
        t4.setText("Customer Pincode : "+FarmerVieworders.pin);
        t5.setText("Customer District : "+FarmerVieworders.dist);
        t6.setText("Purchased Crop Name : "+FarmerVieworders.cn);
        t7.setText("Purchased Quantity : "+FarmerVieworders.quant);

        t8.setText("Total Amount : "+FarmerVieworders.rate);


//        JsonReq JR=new JsonReq();
//        JR.json_response=(JsonResponse)Login.this;
//        String q="/login?username="+username+"&password="+password;
//        q = q.replace(" ", "%20");
//        JR.execute(q);
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),FarmerHome.class);
        startActivity(b);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {


                Toast.makeText(getApplicationContext(), "Order dispatch Successfull", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), FarmerHome.class));
            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}