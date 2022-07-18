package android.example.apipost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Variables for our edittext, button
    EditText nameText, emailText;
    Button saveButton;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the views
        nameText = findViewById(R.id.name);
        emailText = findViewById(R.id.email);
        saveButton = findViewById(R.id.save);

        // setting OnClickListener to our button.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if the text field is empty or not.
                if (nameText.getText().toString().isEmpty() && emailText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Method to post the data and passing our Name and Email.
                createPost(nameText.getText().toString(), emailText.getText().toString());
            }
        });



    }

    private void createPost(String name, String email) {


        // Creating a retrofit builder and passing our base url
        // We are sending data in json format so we have to add Gson converter factory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hotify.in/android/test_json_query/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Created an instance for our JSONPlaceholder class.
        JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

        // Passing data from our text fields to our model class.
        Post post = new Post(name, email);

        // Method to create a post and passing our model class.
        Call<Post> call = jsonPlaceholder.createPost(post, context.getString(R.string.key_secret));

        try{
            // Execution of our method.
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {

                    Toast.makeText(MainActivity.this, response.code() + "Not Successful" , Toast.LENGTH_SHORT).show();

                    // Setting both of the edit text empty.
                    nameText.setText("");
                    emailText.setText("");

                    //
                    // we are getting response from our body and passing it to our model class.
                    Post responseFromAPI = response.body();

                    // on below line we are getting our data from model class and adding it to our string.
                    String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Email : " + responseFromAPI.getEmail();

                    // Setting the string to Toast message.
                    Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    // Setting text to our text view when we get error response from API.
                    Toast.makeText(MainActivity.this, t.getMessage() + "Failed" , Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}