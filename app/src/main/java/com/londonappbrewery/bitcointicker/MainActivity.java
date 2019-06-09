package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mPriceTextView =  findViewById(R.id.priceLabel);
        Spinner spinner = findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  Log.d("spina", "Selected: " + parent.getItemAtPosition(position));
                 // Toast.makeText(getApplicationContext(),"item selected",Toast.LENGTH_SHORT).show();
                  letsDoSomeNetworking(parent.getItemAtPosition(position).toString());
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {
                  Log.d("spina", "nothing selected");
                 // Toast.makeText(getApplicationContext(),"nothing selected",Toast.LENGTH_SHORT).show();
              }
          }


        );


    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        String tempString = BASE_URL + url;
        client.get(tempString, new JsonHttpResponseHandler() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("spina", "Request success");
                Log.d("spina" , "" + response);
                updateUI(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, e, errorResponse);
                Log.d("spina", "Request fail! Status code: " + statusCode);
                Log.d("spina", "Fail response: " + errorResponse);
                Log.e("ERROR", e.toString());
            }
        });


    }

    private void updateUI(JSONObject response)
    {
        String name;
        try
        {
            name = response.getString("last");
            mPriceTextView.setText(name);
            //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e) {
            e.printStackTrace();

        }
    }

}
