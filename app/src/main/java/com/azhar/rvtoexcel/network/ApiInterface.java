package com.azhar.rvtoexcel.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Azhar Rivaldi on 07-09-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public interface ApiInterface {

    @GET("tv/on_the_air?")
    Call<String> getDiscoverTv(@Query("api_key") String API_KEY,
                               @Query("language") String language);

}
