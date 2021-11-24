package co.fouani.octipulse.data.api

import co.fouani.healthinfo.data.models.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APInterface {
    @GET("/api/v1/posts")
    fun getPosts(): Call<List<Post>>
    //fun getPosts(@Path("version") path: String): Call<List<Post>>

}