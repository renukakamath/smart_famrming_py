package com.example.crop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Cropsuggests extends AppCompatActivity implements  JsonResponse {

    EditText e1,e2,e3,e4,e5;
    Button b1;
    String mois,phv,ni,ph,pot;
    TextView t1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropsuggests);

        e1=(EditText)findViewById(R.id.etmois);
        e2=(EditText)findViewById(R.id.etphv);
        e3=(EditText)findViewById(R.id.etni);
        e4=(EditText)findViewById(R.id.etph);
        e5=(EditText)findViewById(R.id.etpot);
        t1=(TextView)findViewById(R.id.textView4);

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mois=e1.getText().toString();
                phv=e2.getText().toString();
                ni=e3.getText().toString();
                ph=e4.getText().toString();
                pot=e5.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Cropsuggests.this;
                String q = "/suggestcropss?mois="+mois+"&phv="+phv+"&ni="+ni+"&ph="+ph+"&pot="+pot;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String method = jo.getString("method");


            if (method.equalsIgnoreCase("suggestcropss")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    t1.setText("Crop Suggested is : "+jo.getString("result"));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed!!!", Toast.LENGTH_LONG).show();
                }
            }



        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}