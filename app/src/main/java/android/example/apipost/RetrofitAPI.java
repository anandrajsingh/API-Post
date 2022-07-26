package android.example.apipost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("users")
    Call<Post> createPost(@Body Post post);
}
