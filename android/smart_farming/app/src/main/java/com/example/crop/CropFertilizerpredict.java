package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CropFertilizerpredict extends AppCompatActivity implements JsonResponse{
    EditText e1;
    Button b1;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_fertilizerpredict);

        e1=(EditText)findViewById(R.id.etplace);
        b1=(Button)findViewById(R.id.btpredict);
        t1=(TextView)findViewById(R.id.tvout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) CropFertilizerpredict.this;
                String q = "/cropfertpredict?place="+e1.getText().toString();
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });


    }

    @Override
    public void response(JSONObject jo) {


        try {

            String method = jo.getString("method");


            if (method.equalsIgnoreCase("cropfertpredict")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    t1.setText(jo.getString("outl")+"\n"+jo.getString("outw")+"\n"+jo.getString("outd")+"\n"+jo.getString("outp")+"\nPredicted Output is : "+jo.getString("out"));
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