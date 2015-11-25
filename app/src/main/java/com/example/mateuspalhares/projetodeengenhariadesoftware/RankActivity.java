package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.mateuspalhares.projetodeengenhariadesoftware.R.*;


public class RankActivity extends Activity {
    private Rank rank = new Rank();

    private MySQLiteHelper db;
    private ArrayList<Rank> arraylstRank = new ArrayList<Rank>();
    private ListView listView;
    private RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_rank);
        listView = (ListView)findViewById(R.id.listView);

        db = new MySQLiteHelper(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            rank.setNome((String) bundle.get("nome"));
            rank.setRank((int) bundle.get("rank"));
            db.InsertRank(rank.getNome(), rank.getRank());
        }



        List<Rank> ranks = db.GetRanks();
        for(Rank rank : ranks){
            arraylstRank.add(rank);
        }

        rankAdapter = new RankAdapter(this, R.layout.display_rank_list,arraylstRank);
        listView.setAdapter(rankAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
