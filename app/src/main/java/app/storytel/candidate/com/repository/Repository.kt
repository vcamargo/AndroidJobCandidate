package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.Post
import app.storytel.candidate.com.webservice.Webservice
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers

class Repository : IRepository {

    private val webservice: Webservice = Webservice.create()

    override fun getComments(postId: Int, callback: SingleObserver<List<Comment>>) {
        webservice.getComments(postId)
            .subscribeOn(Schedulers.newThread())
            .subscribe(callback)
    }

    override fun getPosts(callback: SingleObserver<List<Post>>) {
        webservice.getPosts()
            .subscribeOn(Schedulers.newThread())
            .subscribe(callback)
    }

    override fun getPhotos(callback: SingleObserver<List<Photo>>) {
        webservice.getPhotos()
            .subscribeOn(Schedulers.newThread())
            .subscribe(callback)
    }
}