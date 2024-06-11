package com.example.crop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Custimage2 extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] fn;
	private String[] ln;

	private String[]  ph;
	private String[] em;
	private String[] tit;
	private String[] de;
	private String[] image;
	private String[] type;


	 public Custimage2(Activity context, String[] fname, String[] lname, String[] phone, String[] email, String[] title, String[] des, String[] img , String[] typ){
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_imagess, img);
	        this.context = context;
	        this.image = img;
		    this.fn = fname;

		 	this.ln = lname;
		 	this.ph = phone;
			 this.em=email;
		 this.tit=title;
		 this.de=des;
		 this.type=typ;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_imagess, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

//			TextView t2=(TextView)listViewItem.findViewById(R.id.textView5);
//			TextView t3=(TextView)listViewItem.findViewById(R.id.textView6);
//			TextView t4=(TextView)listViewItem.findViewById(R.id.textView7);
//			Toast.makeText(getContext(),"Product : "+pro[position]+"\nDetails : "+ dtls[position] +"\nQuantity : "+qtys[position]+"\nRate : "+rate[position],Toast.LENGTH_SHORT).
			t1.setText("Officer Name : "+fn[position]+ln[position]+"\nPhone : "+ ph[position] +"\nEmail : "+em[position]+"\nTitle : "+tit[position]+"\nDescription : "+de[position]+"\nType : "+type[position]);
//			t2.setText(cat[position]);
//			t3.setText(qtys[position]);
//			t4.setText(rate[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}