package neoit.cpimpadayatra.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;


public class AddMemberDetails extends AppCompatActivity {
    EditText et_name,et_emailid,et_phno,et_address;
    Button btn_submit,btn_take_img;

    RequestQueue queue;

    public String name;
    ImageView iv_img;
    private static int RESULT_LOAD_IMAGE = 1;
    byte img_store[]=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember_details);
        getSupportActionBar().setTitle("Add Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_name=(EditText)findViewById(R.id.et_name);
        et_emailid=(EditText)findViewById(R.id.et_emailid);
        et_phno=(EditText)findViewById(R.id.et_phno);
        et_address=(EditText)findViewById(R.id.et_address);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_take_img=(Button)findViewById(R.id.btn_take_img);
        iv_img=(ImageView)findViewById(R.id.iv_img);


        iv_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                img_store=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
                /*Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);*/
            }
        });
        btn_take_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                img_store=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
               /* Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);*/
            }
        });


        btn_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadImage(img_store);
               /* int day = dpDob.getDayOfMonth();
                int month = dpDob.getMonth() + 1;
                int year = dpDob.getYear();
                dob=day+"-"+month+"-"+year;
                name=etName.getText().toString();
                age=etAge.getText().toString();
                phone=etPhone.getText().toString();
                email=etMail.getText().toString();
                gender = ((RadioButton)findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();


                if (!isName(name)) {
                    etName.setError("Name should not be empty.");
                    return;
                }

                if (!isAge(age)) {
                    etAge.setError("Age should not be empty.");
                    return;
                }

                if (!isPhone(phone)) {
                    etAge.setError("Age should not be empty.");
                    return;
                }
                if (!isEmail(email)) {
                    etMail.setError("EmailID should not be empty.");
                    return;
                }

                if(img_store!=null){
					*//*db=new DatabaseHandler(getApplicationContext());
					long rec=db.addAdvertisingPerson(name,age,gender,dob,phone,email,img_store);*//*

                }
                else{
                    Toast.makeText(getApplicationContext(), "Image should not be empty.", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

    }
    private boolean isName(String name) {
        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
    }
    private boolean isAge(String age) {
        if (age != null && age.length() > 0) {
            return true;
        }
        return false;
    }
    private boolean isPhone(String phno) {
        if (phno != null && phno.length() > 0) {
            return true;
        }
        return false;
    }
    private boolean isEmail(String email) {
        if (email != null && email.length() > 0) {
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE){
            try {
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 65, stream);
                img_store= stream.toByteArray();
                iv_img.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 65, stream);
                img_store= stream.toByteArray();
                iv_img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
    String encodedImage;
    ProgressDialog pd;
    private void uploadImage(byte[] img){
        //String encodedImage = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        pd=new ProgressDialog(AddMemberDetails.this);
        pd.setTitle("Please wait,data is being uploaded to server.");
        pd.show();
        encodedImage = Base64.encodeToString(img, Base64.DEFAULT);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_MEM_DET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                et_name.getText().clear();
                et_emailid.getText().clear();
                et_phno.getText().clear();
                et_address.getText().clear();
                iv_img.setBackgroundResource(R.drawable.ic_no_image);
                Toast.makeText(getApplicationContext(),"Details are added successfully.",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",et_name.getText().toString());
                params.put("emailid",et_emailid.getText().toString());
                params.put("phno",et_phno.getText().toString());
                params.put("address",et_address.getText().toString());
                params.put("img_url",encodedImage);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
