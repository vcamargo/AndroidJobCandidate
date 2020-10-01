package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.Post
import app.storytel.candidate.com.model.PostAndPhoto
import io.reactivex.SingleObserver

interface IRepository {
    fun getComments(postId: Int, callback: SingleObserver<List<Comment>>)
    fun getPosts(callback: SingleObserver<List<PostAndPhoto>>)
    fun getPhotos(callback: SingleObserver<List<Photo>>)
}