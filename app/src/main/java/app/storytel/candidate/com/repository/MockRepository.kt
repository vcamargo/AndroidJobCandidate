package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MockRepository : IRepository {

    override fun getPosts(): Single<List<PostAndPhoto>> {
        return Single.just(postsMockData).delay(2, TimeUnit.SECONDS)
    }

    override fun getPhotos(): Single<List<Photo>> {
        return Single.just(photosMockData).delay(2, TimeUnit.SECONDS)
    }

    override fun getPostAndPhoto(): Single<List<PostAndPhoto>> {
        return Single.just(postsMockData).delay(2, TimeUnit.SECONDS)
    }

    override fun getComments(postId: Int): Single<List<Comment>> {
        return Single.just(commentsMockData).delay(2, TimeUnit.SECONDS)
    }
}