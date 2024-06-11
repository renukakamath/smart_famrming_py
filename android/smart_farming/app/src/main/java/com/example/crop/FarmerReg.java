package com.example.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

public class FarmerReg extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e6,e7,e8,e9,e10,e11,e12;
    Button b1;
    RadioButton r1,r2;
    String fname,lname,place,pincode,gender,dob,phone,email,latitude,longitude,uname,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_reg);

        startService(new Intent(getApplicationContext(),LocationService.class));
        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);
        e3=(EditText) findViewById(R.id.place);
        e4=(EditText) findViewById(R.id.pincode);
        e6=(EditText) findViewById(R.id.dob);
        e7=(EditText) findViewById(R.id.phone);
        e8=(EditText) findViewById(R.id.email);

        b1=(Button) findViewById(R.id.fbtn);
        r1=(RadioButton) findViewById(R.id.r1);
        r2=(RadioButton) findViewById(R.id.r2);
        e11=(EditText) findViewById(R.id.uname);
        e12=(EditText) findViewById(R.id.pwd);


        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                place = e3.getText().toString();
                pincode = e4.getText().toString();
                if (r1.isChecked()) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                dob = e6.getText().toString();
                phone = e7.getText().toString();
                email = e8.getText().toString();

                uname = e11.getText().toString();
                pwd = e12.getText().toString();

                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Firstname");
                    e1.setFocusable(true);
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Your lastname");
                    e2.setFocusable(true);
                }else if (place.equalsIgnoreCase("")) {
                    e3.setError("Enter Your Place");
                    e3.setFocusable(true);
                }else if (pincode.equalsIgnoreCase("") || pincode.length()!=6) {
                    e4.setError("Enter Your Pincode");
                    e4.setFocusable(true);
                }else if (dob.equalsIgnoreCase("")) {
                    e6.setError("Enter Your Date of Birth");
                    e6.setFocusable(true);
                }else if (phone.equalsIgnoreCase("") || phone.length()!=10) {
                    e7.setError("Enter Your Phone No");
                    e7.setFocusable(true);
                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                    e8.setError("Enter Your Email");
                    e8.setFocusable(true);
                }else if (uname.equalsIgnoreCase("")) {
                    e11.setError("Enter Your Username");
                    e11.setFocusable(true);
                }
                else if (pwd.equalsIgnoreCase("")) {
                    e12.setError("Enter Your Password");
                    e12.setFocusable(true);
                }else {

                    //   Toast.makeText(getApplicationContext(), "firstname="+fname+"last name="+lname+"place"+place+"pincode"+pincode+"gender"+gender+"date of birth"+dob+"phone"+phone+"email"+email+"latitude"+latitude+"longitude"+longitude,Toast.LENGTH_LONG).show();

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) FarmerReg.this;
                    String q = "/farmer?fname=" + fname + "&lname=" + lname + "&place=" + place + "&pincode=" + pincode + "&gender=" + gender + "&dob=" + dob + "&phone=" + phone + "&email=" + email + "&latitude=" + LocationService.lati + "&longitude=" + LocationService.logi + "&username=" + uname + "&password=" + pwd;
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