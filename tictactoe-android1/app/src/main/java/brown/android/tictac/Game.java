package brown.android.tictac;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends Activity {

    int counter = 2;
    int yug_num = 0;
    int rug_num = 0;
    int f11, f12, f13, f21, f22, f23, f31, f32, f33;
    TextView win_name,oggyre_s,roachre_s;
    Button r11,r12,r13,r21,r22,r23,r31,r32,r33;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        win_name = (TextView) findViewById(R.id.win_name);
        oggyre_s = (TextView) findViewById(R.id.oggyre_s);
        roachre_s = (TextView) findViewById(R.id.roachre_s);
        r11 = (Button) findViewById(R.id.r11);
        r12 = (Button) findViewById(R.id.r12);
        r13 = (Button) findViewById(R.id.r13);
        r21 = (Button) findViewById(R.id.r21);
        r22 = (Button) findViewById(R.id.r22);
        r23 = (Button) findViewById(R.id.r23);
        r31 = (Button) findViewById(R.id.r31);
        r32 = (Button) findViewById(R.id.r32);
        r33 = (Button) findViewById(R.id.r33);

    }

    public void c11(View view) {

        if (counter % 2 == 0) {

            r11.setBackgroundResource(R.drawable.sm_oggy);
            r11.setEnabled(false);
            f11 = 1;

        }

        if (counter % 2 != 0) {
            r11.setBackgroundResource(R.drawable.sm_roaches);
            r11.setEnabled(false);
            f11 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);
    }

    public void c12(View view) {

        if (counter % 2 == 0) {
            r12.setBackgroundResource(R.drawable.sm_oggy);
            r12.setEnabled(false);
            f12 = 1;
        }

        if (counter % 2 != 0) {
            r12.setBackgroundResource(R.drawable.sm_roaches);
            r12.setEnabled(false);
            f12 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c13(View view) {

        if (counter % 2 == 0) {
            r13.setBackgroundResource(R.drawable.sm_oggy);
            r13.setEnabled(false);
            f13 = 1;
        }

        if (counter % 2 != 0) {
            r13.setBackgroundResource(R.drawable.sm_roaches);
            r13.setEnabled(false);
            f13 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c21(View view) {

        if (counter % 2 == 0) {
            r21.setBackgroundResource(R.drawable.sm_oggy);
            r21.setEnabled(false);
            f21 = 1;
        }

        if (counter % 2 != 0) {
            r21.setBackgroundResource(R.drawable.sm_roaches);
            r21.setEnabled(false);
            f21 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c22(View view) {

        if (counter % 2 == 0) {
            r22.setBackgroundResource(R.drawable.sm_oggy);
            r22.setEnabled(false);
            f22 = 1;
        }

        if (counter % 2 != 0) {
            r22.setBackgroundResource(R.drawable.sm_roaches);
            r22.setEnabled(false);
            f22 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c23(View view) {

        if (counter % 2 == 0) {
            r23.setBackgroundResource(R.drawable.sm_oggy);
            r23.setEnabled(false);
            f23 = 1;
        }

        if (counter % 2 != 0) {
            r23.setBackgroundResource(R.drawable.sm_roaches);
            r23.setEnabled(false);
            f23 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c31(View view) {

        if (counter % 2 == 0) {
            r31.setBackgroundResource(R.drawable.sm_oggy);
            r31.setEnabled(false);
            f31 = 1;
        }

        if (counter % 2 != 0) {
            r31.setBackgroundResource(R.drawable.sm_roaches);
            r31.setEnabled(false);
            f31 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c32(View view) {

        if (counter % 2 == 0) {
            r32.setBackgroundResource(R.drawable.sm_oggy);
            r32.setEnabled(false);
            f32 = 1;
        }

        if (counter % 2 != 0) {
            r32.setBackgroundResource(R.drawable.sm_roaches);
            r32.setEnabled(false);
            f32 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }

    public void c33(View view) {

        if (counter % 2 == 0) {
            r33.setBackgroundResource(R.drawable.sm_oggy);
            r33.setEnabled(false);
            f33 = 1;
        }

        if (counter % 2 != 0) {
            r33.setBackgroundResource(R.drawable.sm_roaches);
            r33.setEnabled(false);
            f33 = -1;
        }

        counter++;
        winc(f11, f12, f13, f21, f22, f23, f31, f32, f33);

    }


    public void winc(int f11, int f12, int f13, int f21, int f22, int f23, int f31, int f32, int f33) {
        //rows sum
        if (f11 + f12 + f13 == 3)
        {
            //seeNext("Oggy wins!!");
            yug_num++;
           Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();
            yoggy(yug_num);
            request("oggy wins this round");
        }
        if (f21 + f22 + f23 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        if (f31 + f32 + f33 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        //cols sum
        if (f11 + f21 + f31 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        if (f12 + f22 + f32 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        if (f13 + f23 + f33 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        //diag sum
        if (f11 + f22 + f33 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
        if (f13 + f22 + f31 == 3) {
            Toast.makeText(getApplicationContext(),"oggy wins",Toast.LENGTH_LONG).show();

            //seeNext("Oggy wins!!");
            yug_num++;
            yoggy(yug_num);
            request("oggy wins this round");
        }
// cockroaches cases!!!!!!!!!!
        //rows sum
        if (f11 + f12 + f13 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        if (f21 + f22 + f23 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        if (f31 + f32 + f33 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        //cols sum
        if (f11 + f21 + f31 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        if (f12 + f22 + f32 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        if (f13 + f23 + f33 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        //diag sum
        if (f11 + f22 + f33 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
        if (f13 + f22 + f31 == -3) {
            Toast.makeText(getApplicationContext(),"cockroach wins",Toast.LENGTH_LONG).show();

           seeNext("Cockroaches wins!!");
            rug_num++;
            yroach(rug_num);
            request("Cockroaches wins this round");
        }
       else if (( f11!=0) && (f12!=0) && (f13!=0) && (f21!=0) && (f22!=0) && (f23!=0) && (f31!=0) && (f32!=0) && (f33!=0) ){
            Toast.makeText(getApplicationContext(),"draw",Toast.LENGTH_LONG).show();

            request("draw match");
        }
    }

    public void new_game(View view) {

        counter = 2;
        r11.setBackgroundResource(R.drawable.blank);
        r11.setEnabled(true);
        r12.setBackgroundResource(R.drawable.blank);
        r12.setEnabled(true);
        r13.setBackgroundResource(R.drawable.blank);
        r13.setEnabled(true);
        r21.setBackgroundResource(R.drawable.blank);
        r21.setEnabled(true);
        r22.setBackgroundResource(R.drawable.blank);
        r22.setEnabled(true);
        r23.setBackgroundResource(R.drawable.blank);
        r23.setEnabled(true);
        r31.setBackgroundResource(R.drawable.blank);
        r31.setEnabled(true);
        r32.setBackgroundResource(R.drawable.blank);
        r32.setEnabled(true);
        r33.setBackgroundResource(R.drawable.blank);
        r33.setEnabled(true);

        f11 = 0;
        f12 = 0;
        f13 = 0;
        f21 = 0;
        f22 = 0;
        f23 = 0;
        f31 = 0;
        f32 = 0;
        f33 = 0;

    }

    public void reset_score(View view) {
        yoggy(0);
        yroach(0);
        yug_num=0;
        rug_num=0;
        f11 = 0;
        f12 = 0;
        f13 = 0;
        f21 = 0;
        f22 = 0;
        f23 = 0;
        f31 = 0;
        f32 = 0;
        f33 = 0;

        counter = 2;
        r11.setBackgroundResource(R.drawable.blank);
        r11.setEnabled(true);
        r12.setBackgroundResource(R.drawable.blank);
        r12.setEnabled(true);
        r13.setBackgroundResource(R.drawable.blank);
        r13.setEnabled(true);
        r21.setBackgroundResource(R.drawable.blank);
        r21.setEnabled(true);
        r22.setBackgroundResource(R.drawable.blank);
        r22.setEnabled(true);
        r23.setBackgroundResource(R.drawable.blank);
        r23.setEnabled(true);
        r31.setBackgroundResource(R.drawable.blank);
        r31.setEnabled(true);
        r32.setBackgroundResource(R.drawable.blank);
        r32.setEnabled(true);
        r33.setBackgroundResource(R.drawable.blank);
        r33.setEnabled(true);


    }

    private void seeNext(String message) {

        win_name.setText(message);
    }

    public void yoggy(int yug_num) {


        oggyre_s.setText(yug_num+"");

    }

    public void yroach(int rug_num) {


        roachre_s.setText(rug_num+"");


    }
    public void request(String s){


        final AlertDialog.Builder al =  new AlertDialog.Builder(this);

        al.setMessage(s+" \n do u want to continue")
                .setCancelable(false)
                .setPositiveButton("ok continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new_game(findViewById(android.R.id.content));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("no reset game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset_score(findViewById(android.R.id.content));
                        dialog.cancel();
                    }
                });
        AlertDialog ad = al.create();
                ad.show();}


}


