package co.fouani.octipulse.data.api

import android.content.Context
import co.fouani.healthinfo.BuildConfig.api_url
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.utils.Utility
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//client
class APIClient(val context: Context) {
    val BASE_URL: String = api_url
    var apInterface: APInterface
    var onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 5 * 60 // read from cache for 5 minutes even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
    var offlineInterceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!Utility.isNetworkAvailable(context)) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }

    init {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val client = OkHttpClient.Builder()
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(myCache)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.apInterface = retrofit.create(APInterface::class.java)
    }


    fun getPosts(): Call<List<Post>>? {
        return apInterface.getPosts( )
    }


}