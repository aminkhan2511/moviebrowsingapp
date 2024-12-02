package com.example.moviebrowsingapp.data.remote



import com.example.moviebrowsingapp.util.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

//    here we are making singleton object for retrofit
    companion object {
        // lazy - initialize only when it is called and return the same object every time


        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        private val retrofit by lazy {

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val movieApi by lazy {
            retrofit.create(MovieApisInterface::class.java)
        }
    }
}