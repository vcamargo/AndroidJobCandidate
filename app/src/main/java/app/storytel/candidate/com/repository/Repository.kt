package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.webservice.Webservice
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class Repository constructor(private val webservice: Webservice) : IRepository {

    companion object {
        const val COMMENTS_NUMBER = 3L
    }

    override fun getComments(postId: Int): Single<List<Comment>> {
        return webservice.getComments(postId)
            .flattenAsObservable { it }
            .take(COMMENTS_NUMBER)
            .toList()
    }

    override fun getPosts(): Single<List<PostAndPhoto>> {
        return webservice.getPosts()
    }

    override fun getPhotos(): Single<List<Photo>> {
        return webservice.getPhotos()
    }

    override fun getPostAndPhoto(): Single<List<PostAndPhoto>> {
        return Single.zip(
            getPosts(), getPhotos(),
            { postList: List<PostAndPhoto>, photoList: List<Photo> ->
                // Reduce Photos List to the same size of Posts List
                val photos = photoList.take(postList.size)

                postList.mapIndexed { index, postAndPhoto ->
                    postAndPhoto.thumbnailUrl = photos[index].thumbnailUrl
                }
                return@zip postList
            })
    }
}