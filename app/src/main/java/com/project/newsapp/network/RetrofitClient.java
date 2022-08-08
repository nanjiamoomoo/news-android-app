package com.project.newsapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This class is responsible for providing a configured Retrofit instance, that can then instantiate a NewsApi implementation
public class RetrofitClient {

    private static final String API_KEY = "521b4c2046b14efa9f1a131e6ae7e149";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //Header interceptor: attached custom or standard header information to all requests
                .addInterceptor(new HeaderInterceptor())
                .build();
        return new Retrofit.Builder()
                //Set the API base URL.
                .baseUrl(BASE_URL)
                //Add converter factory for serialization and deserialization of objects.
                //Using GSON to deserialize JSON response into model classes
                .addConverterFactory(GsonConverterFactory.create())
                //The HTTP client used for requests.
                .client(okHttpClient)
                //Create the Retrofit instance using the configured values.
                .build();
    }

    //Observes, modifies, and potentially short-circuits requests going out and the corresponding responses coming back in.
    //Typically interceptors add, remove, or transform headers on the request or response.
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //A HTTP Request
            Request original = chain.request();
            Request request = original
                    //return a request builder
                    .newBuilder()
                    //you can provide API key via X-Api-Key HTTP Header
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
