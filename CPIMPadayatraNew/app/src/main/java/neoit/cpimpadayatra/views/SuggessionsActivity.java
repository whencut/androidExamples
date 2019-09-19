package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

    EditText et_suggessions,et_name,et_email_id_phone;
    Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggessions);
        getSupportActionBar().setTitle("Suggessions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_name= (EditText) findViewById(R.id.et_name);
        et_suggessions= (EditText) findViewById(R.id.et_suggessions);
        et_email_id_phone= (EditText) findViewById(R.id.et_email_id_phone);
        btn_submit= (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitDataToServer();

            }
        });
    }

    ProgressDialog pb;
    private void submitDataToServer(){
        pb=new ProgressDialog(SuggessionsActivity.this);
        pb.setMessage("Please wait,Data is being submitted...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SUGESSIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Toast.makeText(getApplicationContext(),"Your feedback is submitted successfully.",Toast.LENGTH_SHORT).show();
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

                params.put("email", et_name.getText().toString());
                params.put("sugg",  et_suggessions.getText().toString());
                params.put("email_id_phone",  et_email_id_phone.getText().toString());

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

