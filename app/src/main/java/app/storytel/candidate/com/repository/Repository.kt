package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.webservice.Webservice
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Repository(private val webservice: Webservice) : IRepository {

    override fun getComments(postId: Int) : Single<List<Comment>> {
            return webservice.getComments(postId)
                .subscribeOn(Schedulers.io())
    }

    override fun getPosts() : Single<List<PostAndPhoto>> {
        return webservice.getPosts().subscribeOn(Schedulers.io())
    }

    override fun getPhotos() : Single<List<Photo>> {
        return webservice.getPhotos().subscribeOn(Schedulers.io())
    }

    override fun getPostAndPhoto() : Single<List<PostAndPhoto>> {
        return Single.zip(
                getPosts(), getPhotos(),
            { postList: List<PostAndPhoto>, photoList: List<Photo> ->
                // Reduce Photos List to the same size of Posts List
                val photos = photoList.take(postList.size)

                postList.mapIndexed { index, postAndPhoto ->
                    postAndPhoto.thumbnailUrl = photos[index].thumbnailUrl
                }
                return@zip postList
            }).subscribeOn(Schedulers.io())
    }
}