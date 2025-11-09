package com.example.projeeeeeeeeeect.network;

import android.content.Context;
import android.util.Log;

import com.example.projeeeeeeeeeect.auth.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static Retrofit retrofit = null;
    private static SessionManager sessionManager;

    public static ApiService getApiService(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }

        if (retrofit == null) {
            // 🔐 Auth header interceptor
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String token = sessionManager.getAuthToken();
                    Request original = chain.request();

                    if (token != null) {
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }

                    return chain.proceed(original);
                }
            };

            // 🔍 Logging interceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("RetrofitLog", message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 🧪 Build OkHttpClient with both interceptors
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // 🚀 Build Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}