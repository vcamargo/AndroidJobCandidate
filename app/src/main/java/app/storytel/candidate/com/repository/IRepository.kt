package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import io.reactivex.Single

interface IRepository {
    fun getPosts(): Single<List<PostAndPhoto>>
    fun getPhotos(): Single<List<Photo>>
    fun getPostAndPhoto(): Single<List<PostAndPhoto>>
    fun getComments(postId: Int): Single<List<Comment>>
}