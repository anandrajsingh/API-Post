package android.example.apipost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface JSONPlaceholder {

    @GET
    Call<String> getStringResponse(@Url String url);

    @POST("test_json_query")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @PUT("test_json_query")
    Call<String> postString(@Field("name") String name, @Field("email") String email);


}
