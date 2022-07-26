package android.example.apipost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Variables for our edittext, button
    EditText nameText, emailText;
    Button saveButton, nextButton;

    TextView textView;

    JSONPlaceholder jsonPlaceholder;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the views
        nameText = findViewById(R.id.name);
        emailText = findViewById(R.id.email);
        saveButton = findViewById(R.id.save);
        nextButton = findViewById(R.id.next);
        textView = findViewById(R.id.textView);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReqresActivity.class);
                startActivity(intent);
            }
        });

      // Creating a retrofit builder and passing our base url
      // We are sending data in json format so we have to add Gson converter factory
      Retrofit retrofit = new Retrofit.Builder()
              .addConverterFactory(ScalarsConverterFactory.create())
              .addConverterFactory(GsonConverterFactory.create())
              .baseUrl("https://hotify.in/android/")
              .build();

      // Created an instance for our JSONPlaceholder class.
      jsonPlaceholder = retrofit.create(JSONPlaceholder.class);


//      getResponse();


      // setting OnClickListener to our button.
      saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              // Checking if the text field is empty or not.
              if (nameText.getText().toString().isEmpty() || emailText.getText().toString().isEmpty()) {
                  Toast.makeText(MainActivity.this, "Enter Both the Value", Toast.LENGTH_SHORT).show();
              }

              // Method to post the data and passing our Name and Email.
//              createPost(nameText.getText().toString(), emailText.getText().toString());

              postString(nameText.getText().toString(), emailText.getText().toString());
          }
      });

    }


    private void createPost(String name, String email) {


        String key_secret = "testkey@2022";

        // Passing data from our text fields to our model class.
        Post post = new Post(name, email);

        // Method to create a post and passing our model class.
        Call<Post> call = jsonPlaceholder.createPost(post);

        try{
            // Execution of our method.
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {

                    // Setting both of the edit text empty.
                    nameText.setText("");
                    emailText.setText("");

                    //
                    // we are getting response from our body and passing it to our model class.
                    Post responseFromAPI = response.body();

                    // on below line we are getting our data from model class and adding it to our string.
                    String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Email : " + responseFromAPI.getEmail();

                    // Setting the string to Toast message.
                    textView.setText(responseString);
                    Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    // Setting text to our text view when we get error response from API.
                    Toast.makeText(MainActivity.this, t.getMessage() + "Failed" , Toast.LENGTH_SHORT).show();
                    Log.d("demo",t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void postString(String name, String email) {


        String key_secret = "testkey@2022";

        // Passing data from our text fields to our model class.
        String post = "Name: " + name +" Email: "+ email+ key_secret;

        // Method to create a post and passing our model class.
        Call<String> call = jsonPlaceholder.postString(name, email);

        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Successful ", "In postString");

                    // Setting both of the edit text empty.

                    nameText.setText("");
                    emailText.setText("");

                    String responseString = response.body().toString();
                    textView.setText(responseString);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("postString",t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}