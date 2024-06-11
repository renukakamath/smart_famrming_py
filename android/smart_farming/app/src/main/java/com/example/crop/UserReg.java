package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserReg extends AppCompatActivity implements JsonResponse {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12;
    Button b1;
    String fname,lname,place,latitude,longitude,phone,email,hname,pincode,district,uname,pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        startService(new Intent(getApplicationContext(),LocationService.class));
        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);

        e5=(EditText) findViewById(R.id.phone);
        e6=(EditText) findViewById(R.id.email);
        e7=(EditText) findViewById(R.id.hname);
        e8=(EditText) findViewById(R.id.place);
        e9=(EditText) findViewById(R.id.pincode);
        e10=(EditText) findViewById(R.id.district);
        e11=(EditText) findViewById(R.id.uname);
        e12=(EditText) findViewById(R.id.pwd) ;
        b1=(Button) findViewById(R.id.ubtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
//                latitude=e3.getText().toString();
//                longitude=e4.getText().toString();
//                GeocodingLocation locationAddress = new GeocodingLocation();
//                locationAddress.getAddressFromLocation(place,
//                        getApplicationContext(), new UserReg().GeocoderHandler());

//                latitude=LocationService.lati;
//                longitude=LocationService.logi;

                phone=e5.getText().toString();
                email=e6.getText().toString();
                hname=e7.getText().toString();
                place=e8.getText().toString();
                pincode=e9.getText().toString();
                district=e10.getText().toString();
                uname=e11.getText().toString();
                pwd=e12.getText().toString();

                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Firstname");
                    e1.setFocusable(true);
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Your lastname");
                    e2.setFocusable(true);
                }else if (place.equalsIgnoreCase("")) {
                    e8.setError("Enter Your Place");
                    e8.setFocusable(true);
                }else if (pincode.equalsIgnoreCase("") || pincode.length()!=6) {
                    e9.setError("Enter Your Pincode");
                    e9.setFocusable(true);
                }else if (hname.equalsIgnoreCase("")) {
                    e7.setError("Enter Your houseName");
                    e7.setFocusable(true);
                }else if (phone.equalsIgnoreCase("") || phone.length()!=10) {
                    e5.setError("Enter Your Phone No");
                    e5.setFocusable(true);
                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                    e6.setError("Enter Your Email");
                    e6.setFocusable(true);
                }else if (uname.equalsIgnoreCase("")) {
                    e11.setError("Enter Your Username");
                    e11.setFocusable(true);
                }
                else if (pwd.equalsIgnoreCase("")) {
                    e12.setError("Enter Your Password");
                    e12.setFocusable(true);
                }
                else if (district.equalsIgnoreCase("")) {
                    e10.setError("Enter Your District");
                    e10.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserReg.this;
                    String q = "/user?fname=" + fname + "&lname=" + lname + "&latitude=" + LocationService.lati + "&longitude=" + LocationService.logi + "&phone=" + phone + "&email=" + email + "&housename=" + hname + "&place=" + place + "&pincode=" + pincode + "&district=" + district + "&username=" + uname + "&password=" + pwd;
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

            Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),Login.class));



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
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}