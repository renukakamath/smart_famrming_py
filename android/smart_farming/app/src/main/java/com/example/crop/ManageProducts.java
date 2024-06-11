package com.example.crop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ManageProducts extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    Spinner s1;
    SharedPreferences sh;
    Button b1;
    ImageButton i1;
    String[] ctype,cdes,value,typeid;
    String id,crname,crquantity,crdesc,cramount;
    String[] img;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        s1=(Spinner) findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(Button) findViewById(R.id.button2);


        i1 = (ImageButton) findViewById(R.id.imageButton2);


        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageOption();
            }
        });

        EditText e1=(EditText) findViewById(R.id.editTextTextPersonName);
        EditText e2=(EditText) findViewById(R.id.editTextTextPersonName2);
        EditText e3=(EditText) findViewById(R.id.editTextTextPersonName3);
        EditText e4=(EditText) findViewById(R.id.editTextTextPersonName4);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    crname=e1.getText().toString();
                    crquantity=e2.getText().toString();
                    crdesc=e3.getText().toString();
                    cramount=e4.getText().toString();

                    sendAttach();

                }
            });


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)ManageProducts.this;
        String q="/viewcroptype";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }
    private void sendAttach() {

        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");


//                String q = "http://" + IpSetting.ip + "/smart city/apis.php";
            String q = "http://" +IpSetting.text+"/api/farmeraddcrop";

            Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


            Map<String, byte[]> aa = new HashMap<>();

            aa.put("image", byteArray);
            aa.put("type_id",id.getBytes());
            aa.put("crop",crname.getBytes());
            aa.put("quantity",crquantity.getBytes());
            aa.put("description",crdesc.getBytes());
            aa.put("amount",cramount.getBytes());
            aa.put("log_id",sh.getString("log_id","").getBytes());
//            aa.put("house",house.getBytes());

            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) ManageProducts.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }
    private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ManageProducts.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("viewcroptype")) {


                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    typeid = new String[ja1.length()];
                    ctype = new String[ja1.length()];

                    value = new String[ja1.length()];



                    for (int i = 0; i < ja1.length(); i++) {
                        ctype[i] = ja1.getJSONObject(i).getString("type_name");
                        typeid[i] = ja1.getJSONObject(i).getString("type_id");



                        value[i] = ctype[i];
                    }

                }else if (status.equalsIgnoreCase("failed")) {
                    Toast.makeText(getApplicationContext(), "No Orders", Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                s1.setAdapter(ar);
//            Custimage a = new Custimage(this, name, des, image, amount,quantity);
//            l1.setAdapter(a);

            }
            if (method.equalsIgnoreCase("farmacceptorder")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Verification Completed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),FarmerVieworders.class));
                }


            }

            if (method.equalsIgnoreCase("farmeraddcrop")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Crop added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),FarmerHome.class));
                }else if (status.equalsIgnoreCase("failed")) {
                    Toast.makeText(getApplicationContext(), "Crop added Failed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),FarmerHome.class));
                }


            }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        id=typeid[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
//                path = FileUtils.getPath(this, uri);
                path=FileUtils.getPath(this,uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),FarmerHome.class);
        startActivity(b);
    }

}