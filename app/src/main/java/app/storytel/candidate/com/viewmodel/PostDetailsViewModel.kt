package app.storytel.candidate.com.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class PostDetailsViewModel(
    private val repository: IRepository) : ViewModel() {
    var postBody = ObservableField<String>()
    var postImageUrl = ObservableField<String>()

    val comments: ObservableList<Comment> by lazy {
        ObservableArrayList()
    }

    fun loadComments(postId: Int) {
        repository.getComments(postId, LoadCommentsCallback())
    }

    private inner class LoadCommentsCallback : SingleObserver<List<Comment>> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: List<Comment>) {
            comments.addAll(t.take(3))
        }

        override fun onError(e: Throwable) {
        }
    }
}