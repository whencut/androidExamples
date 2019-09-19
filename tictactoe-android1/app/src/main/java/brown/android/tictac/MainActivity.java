package brown.android.tictac;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

/**
 * Created by salimatti on 8/16/2016.
 */
public class MainActivity extends Activity {
    Button b1,b2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        b1= (Button) findViewById(R.id.single);
        b2= (Button) findViewById(R.id.twoplayer);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GameActivity.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Game.class));
            }
        });

    }
    @Override
    public void onBackPressed() {

        //final AlertDialog.Builder al1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        //final AlertDialog.Builder al1 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth));
        final AlertDialog.Builder al1 = new AlertDialog.Builder(this);

        al1.setMessage("do u want to close this application")
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("no no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad = al1.create();

        ad.setTitle("bye byeeee");
        ad.show();

    }
}
