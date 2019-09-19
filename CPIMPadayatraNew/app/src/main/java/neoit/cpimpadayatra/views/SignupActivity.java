package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;
import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    Button btn_register;
    EditText username,email,phoneno,dob,password1,et_address;
    RadioButton gender_m,gender_f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Join with Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_register = (Button) findViewById(R.id.btn_register);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        phoneno = (EditText) findViewById(R.id.phoneno);
        dob = (EditText) findViewById(R.id.dob);
        password1 = (EditText) findViewById(R.id.password1);
        et_address = (EditText) findViewById(R.id.et_address);

        gender_m = (RadioButton) findViewById(R.id.gender_m);
        gender_f = (RadioButton) findViewById(R.id.gender_f);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToServer();
            }
        });
    }

    ProgressDialog pb;
    private void submitDataToServer(){
        pb=new ProgressDialog(SignupActivity.this);
        pb.setMessage("Please wait,Data is being submitted...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedpreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("email",email.getText().toString());
                        editor.commit();
                        pb.hide();
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.hide();
                        Toast.makeText(getApplicationContext(),"Server Down.",Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname", username.getText().toString());
                params.put("emailid", email.getText().toString());
                params.put("phoneno", phoneno.getText().toString());
                params.put("address", et_address.getText().toString());

                params.put("dob", "--");
                if (gender_m.isChecked()){
                    params.put("gender", "male");}
                else {
                    params.put("gender", "female");
                }
                params.put("pwd", "xxx");
                return params;
            }
        };
        queue.add(stringRequest);
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
