package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * Created by salimatti on 8/1/2016.
 */
public class SuggessionsActivity extends AppCompatActivity {
EditText tv_suggessions;
    Button btn_submit;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggessions);
        tv_suggessions= (EditText) findViewById(R.id.tv_suggessions);
        btn_submit= (Button) findViewById(R.id.btn_submit);
        sharedpreferences = getSharedPreferences("user", MODE_PRIVATE);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sharedpreferences.getString("email","zazzz")).equals("zazzz"))
                {
                    Toast.makeText(getApplicationContext(),"please signup first",Toast.LENGTH_LONG).show();
                }
                else {

                    /*Toast.makeText(getApplicationContext(),"succesfully submitted",Toast.LENGTH_LONG).show();
                    finish();*/
                    submitDataToServer();
                }
            }
        });
    }
    ProgressDialog pb;
    private void submitDataToServer(){
        pb=new ProgressDialog(SuggessionsActivity.this);
        pb.setMessage("Please wait,Data is being submitted...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url ="http://whencutwini.coolpage.biz/sff/studentdetails.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SUGESSIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(getApplicationContext(),"Server Down."+error,Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", sharedpreferences.getString("email","zazzz"));
                params.put("sugg", tv_suggessions.getText().toString());
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



}

