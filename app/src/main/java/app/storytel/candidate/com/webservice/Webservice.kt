package app.storytel.candidate.com.webservice

import app.storytel.candidate.com.BuildConfig
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Webservice {

    companion object {
        fun create(): Webservice {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Webservice::class.java)
        }
    }

    @GET("/posts")
    fun getPosts(): Single<List<PostAndPhoto>>

    @GET("/photos")
    fun getPhotos(): Single<List<Photo>>

    @GET("/posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Single<List<Comment>>
}