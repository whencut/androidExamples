package neoit.cpimpadayatra.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import neoit.cpimpadayatra.R;
import neoit.cpimpadayatra.adapters.LeadersAdapter;

/**
 * Created by salimatti on 8/1/2016.
 */
public class LeadersActivity extends AppCompatActivity {
    String[] names,district;
    List<HashMap<String,String>> memberList;
    RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        names = getResources().getStringArray(R.array.names);
        district = getResources().getStringArray(R.array.district);
        HashMap<String,String> hm;
        memberList = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < names.length; i++) {
            hm=new HashMap<String,String>();
            hm.put("name",names[i]);
            hm.put("email",district[i]);
            memberList.add(hm);
        }

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LeadersActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        LeadersAdapter adapter=new LeadersAdapter(memberList,this);
        recyclerview.setAdapter(adapter);
    }
}