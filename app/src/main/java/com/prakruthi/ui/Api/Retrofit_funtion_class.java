package com.prakruthi.ui.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prakruthi.ui.Variables;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_funtion_class {
    private static Retrofit retrofit = null;

    public static synchronized  Retrofit getClient() {


        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .build();

           /* OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImZlNDdiN2FhNDFlYzU1MDY4NjkzNzAzMzVkNzhiZTgyOTUxZWI1ZjY4MDA2NWQ3NzI1ZTcyNTNmZDUzM2IyNmFhZDdjZDhmZDExMWU4ODFmIn0.eyJhdWQiOiIyIiwianRpIjoiZmU0N2I3YWE0MWVjNTUwNjg2OTM3MDMzNWQ3OGJlODI5NTFlYjVmNjgwMDY1ZDc3MjVlNzI1M2ZkNTMzYjI2YWFkN2NkOGZkMTExZTg4MWYiLCJpYXQiOjE1MjczMTc3MjgsIm5iZiI6MTUyNzMxNzcyOCwiZXhwIjoxNTU4ODUzNzI4LCJzdWIiOiIxNzMiLCJzY29wZXMiOlsiKiJdfQ.LAs-T3i-V5rEU7NijF4eV2ah37pG_utL3YxXdIw825ivA7oRZIuFBmyIowj-otPaxDRY01a1EZGJRAmGgE2BxEKWsF4eNFBnMnsm_YvGUhw0LYy4cNnrDxIDQK0Hv_JhHi133FLaHxdYHvw2U2seofuNzVzc9SzzkGl0jOx7ODgCud6e4ibjXgqiWWIeZuArry1H1btkJaIgS_9Bgb-glkbije0BMhyYZ6S6siIlbEss_R6SRkuPtIhU9XpUWqwqTtlX38LPhj7utAGelZuDuuZlsTCYprCDj43m-K2XidlduTMX7QXsV0oWCSM4DWdOvK5Ne9LuJaMj1BKSklMm0XyiJ1efdCEvHQ0Yvz77Tgwm7y3NpPCRyykSUUmba7JnQZ5zdR7ingzr-mxxd-HBgz_zhpZkCkuLZSBNnTjx1NjBgZY0TVRxGmJ2I9ZkHPZvZ-I1mV75tt_DBGE8C0t86jx5cl0D818cJNobELjdGWEqAvCAXC8P54jMygFr2Ks0Y-FfsooVscvp2wUnX4f-JWtqu88HdP9_jPmJgd1SuAQ2SzXAWEXOdzPOvyNmNNBTrgb3lMfiNPxISLM7J6nMDhuBGyOqj9yIm6S8pEyJF7IwnlOt7-y8HO8wocsjeyXGVt9VeBMa6fl2O0DOXltfWe3MNxH_v7TROAYV0nU7XGA").build();
                    return chain.proceed(request);
                }
            });*/

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(DZ_URL.AUTH__BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
