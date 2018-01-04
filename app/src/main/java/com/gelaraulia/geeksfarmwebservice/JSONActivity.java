package com.gelaraulia.geeksfarmwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONActivity extends AppCompatActivity {
    TextView tv_name, tv_city, tv_prov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        tv_name = (TextView)findViewById(R.id.tv_json_name);
        tv_city = (TextView)findViewById(R.id.tv_json_city);
        tv_prov = (TextView)findViewById(R.id.tv_json_prov);

        //Declare JSON Object
        JSONObject json_student = new JSONObject();
        JSONObject json_address = new JSONObject();

        try {
            json_student.put("name","dwikuntobayu");
            json_address.put("city","bandung");
            json_address.put("prov","west java");
            json_student.put("address",json_address);

            tv_name.setText("Name: " + json_student.getString("name"));
            //Set Variable to read JSON Address
            JSONObject json_addr_scatter = (JSONObject)json_student.get("address");
            tv_city.setText("City:" + json_addr_scatter.getString("city"));
            tv_prov.setText("Province: " + json_addr_scatter.getString("prov"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
