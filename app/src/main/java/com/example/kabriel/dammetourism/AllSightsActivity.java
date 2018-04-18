package com.example.kabriel.dammetourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllSightsActivity extends AppCompatActivity {

    String monument_data;
    JSONObject jsonObj;
    JSONArray jsonArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sights);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.mipmap.ic_layout);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        monument_data = getIntent().getStringExtra("monument_data");

        try {
            jsonObj = new JSONObject(monument_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonArr = jsonObj.getJSONArray("Monuments");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int temp = jsonArr.length();
        final String[] mon_name = new String[temp];
        final String[] mon_desc = new String[temp];

        for (int i = 0; i < jsonArr.length(); i++)
        {
            try {
                mon_name[i] = jsonArr.getJSONObject(i).getString("monumentName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                mon_desc[i] = jsonArr.getJSONObject(i).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ImageView image1 = (ImageView) findViewById(R.id.img1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(AllSightsActivity.this ,
                        SightsActivity.class);
                intentMain.putExtra("mon_name", mon_name[0]);
                intentMain.putExtra("mon_desc", mon_desc[0]);
                AllSightsActivity.this.startActivity(intentMain);
                Log.i("Content "," Main layout ");
            }
        });

        TextView text1 = (TextView) findViewById(R.id.txt1);
        text1.setText(mon_name[0]);

        TextView text2 = (TextView) findViewById(R.id.txt2);
        text2.setText(mon_name[1]);

        TextView text3 = (TextView) findViewById(R.id.txt3);
        text3.setText(mon_name[2]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}