package com.yapps.googlepluslogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient googleApiClient;

    int RC_SIGN_IN=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();


        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("vinay",data.getExtras().toString()+"something");

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Log.d("vinay",result.getStatus().getStatusMessage()+"something");

            handleSignInResult(result);
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {

if(result.isSuccess()){

    if (result.isSuccess()) {
        GoogleSignInAccount acct = result.getSignInAccount();
        // Get account information
        String mFullName = acct.getDisplayName();
        String mEmail = acct.getEmail();
    }
    else {
        Log.d("vinay",result.getStatus().getStatusMessage()+"fail");
    }
    Toast.makeText(getApplicationContext(), result.getSignInAccount().getEmail(),Toast.LENGTH_SHORT).show();
        //result.getSignInAccount().getDisplayName();
    }}

}

