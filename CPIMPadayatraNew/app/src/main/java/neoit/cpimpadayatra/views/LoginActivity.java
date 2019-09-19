package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;

/**
 * Created by Neocursor on 25/09/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Admin Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);

        Button btn_login=(Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uname = et_username.getText().toString();
                if (!isUName(uname)) {
                    et_username.setError("User Name should not be empty.");
                    return;
                }
                String pwd = et_password.getText().toString();
                if (!isPWD(pwd)) {
                    et_password.setError("Password should not be empty.");
                    return;
                }
                sendDetailsToServer();

            }
        });

    }

    private boolean isUName(String uname) {
        if (uname != null && uname.length() > 0) {
            return true;
        }
        return false;
    }
    private boolean isPWD(String pwd) {
        if (pwd != null && pwd.length() > 0) {
            return true;
        }
        return false;
    }
    ProgressDialog pd;
    private void sendDetailsToServer(){
        pd=new ProgressDialog(LoginActivity.this);
        pd.setTitle("Please wait,Data is being uploaded");
        pd.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                if(response.equalsIgnoreCase("success")){

                    Intent intent=new Intent(getApplicationContext(),AddMemberDetails.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Username / Password.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                //name,age,mobile,address,uname,pwd,blood_group
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname",et_username.getText().toString());
                params.put("pwd",et_password.getText().toString());

                return params;
            }
        };
        // Add the request to the RequestQueue.
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
