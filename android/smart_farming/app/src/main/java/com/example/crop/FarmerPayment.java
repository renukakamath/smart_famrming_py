package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class FarmerPayment extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4;
    String card,cvv,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_payment);

        e1=(EditText) findViewById(R.id.famount);
        e2=(EditText) findViewById(R.id.fc);
        e3=(EditText) findViewById(R.id.fcvv);
        e4=(EditText) findViewById(R.id.fd);


        e1.setText(ViewBooking.amount);
        Button b1 = (Button)findViewById(R.id.farmerbtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                card=e2.getText().toString();
                cvv=e3.getText().toString();
                date=e4.getText().toString();

                if(card.equalsIgnoreCase("") || card.length()!=16){

                    e2.setError("Enter Your 16 digit Card No ");
                    e2.setFocusable(true);

                }if(cvv.equalsIgnoreCase("") || cvv.length()!=3){

                    e3.setError("Enter Your 3 Digit CVV Number ");
                    e3.setFocusable(true);

                }if(date.equalsIgnoreCase("")){

                    e4.setError("Enter Your Card Expeiry Date ");
                    e4.setFocusable(true);

                }else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) FarmerPayment.this;
                    String q = "/farmmakepayment?bid=" + ViewBooking.booking_id + "&amount=" + ViewBooking.amount;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
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
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();


                Toast.makeText(getApplicationContext(), "Payment Completed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),FarmerHome.class));
            }

//

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