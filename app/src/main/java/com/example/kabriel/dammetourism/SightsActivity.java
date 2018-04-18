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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SightsActivity extends AppCompatActivity {

    Button btnTicketBuy;
    Button btnTicketShow;
    TextView text_desc;
    TextView text_name;
    String mon_name;
    String mon_desc;
    String QR_HASH = null;
    String POST_URL = "http://ptsv2.com/t/kabi/post";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);

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

        mon_name = getIntent().getStringExtra("mon_name");
        mon_desc = getIntent().getStringExtra("mon_desc");

        btnTicketBuy = (Button) findViewById(R.id.btn_buy);
        btnTicketShow = (Button) findViewById(R.id.btn_show);
        text_desc = (TextView) findViewById(R.id.text_desc);
        text_name = (TextView) findViewById(R.id.text_name);

        text_desc.setText(mon_desc);
        text_name.setText(mon_name);

        btnTicketBuy.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentMain = new Intent(SightsActivity.this ,
                        BaseCardFormActivity.class);
                intentMain.putExtra("hash", QR_HASH);
                SightsActivity.this.startActivity(intentMain);
                Log.i("Content "," Main layout ");
            }
        }));

        btnTicketShow.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentMain = new Intent(SightsActivity.this ,
                        TicketActivity.class);
                intentMain.putExtra("hash", QR_HASH);
                SightsActivity.this.startActivityForResult(intentMain, 1);
                Log.i("Content "," Main layout ");
            }
        }));

        text_desc.setText(mon_desc);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest( com.android.volley.Request.Method.POST, POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("hash", QR_HASH);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("test");
                text_desc.setText(strEditText);
            }
        }
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
