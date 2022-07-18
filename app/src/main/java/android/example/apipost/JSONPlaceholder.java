package android.example.apipost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JSONPlaceholder {

    @POST("posts")
    Call<Post> createPost(@Body Post post, @Query("key_secret") String api_key);

//    @FormUrlEncoded
//    @POST("posts")
//    Call<Post> createPost(@Field("name") String name , @Field("email") String email );

}
