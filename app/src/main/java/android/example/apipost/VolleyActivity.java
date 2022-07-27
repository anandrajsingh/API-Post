package android.example.apipost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    private EditText nameEdt, emailEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);


        nameEdt = findViewById(R.id.idEdtName);
        emailEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);


        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdt.getText().toString().isEmpty() || emailEdt.getText().toString().isEmpty()) {
                    Toast.makeText(VolleyActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postDataUsingVolley(nameEdt.getText().toString(), emailEdt.getText().toString());
            }
        });
    }

    private void postDataUsingVolley(String name, String email) {
        String url = "https://hotify.in/android/test_json_query";
      //  String url = "https://reqres.in/api/users";
        loadingPB.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(VolleyActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingPB.setVisibility(View.GONE);
                nameEdt.setText("");
                emailEdt.setText("");

                Toast.makeText(VolleyActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                Log.d("Response before : ", name + " " + email + " " + response);

                responseTV.setText(response);


                try {
                    JSONObject respObj = new JSONObject(response);

                    String name = respObj.getString("name");
                    String email = respObj.getString("email");

                    responseTV.setText("Name : " + name + "\n" + "Job : " + email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(VolleyActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("email", email);

                return params;
            }
        };
        queue.add(request);
    }
}