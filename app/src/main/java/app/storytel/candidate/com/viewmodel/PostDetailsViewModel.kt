package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class PostDetailsViewModel(
    private val repository: IRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val POST_ID_KEY = "POST_ID_KEY"
    }
    var postBody = ObservableField<String>()
    var postImageUrl = ObservableField<String>()

    val comments: ObservableList<Comment> by lazy {
        ObservableArrayList()
    }

    val showLoading = ObservableInt().also { it.set(View.GONE) }
    val noConnVisibility = ObservableInt().also { it.set(View.GONE) }
    val layoutVisibility = ObservableInt().also { it.set(View.GONE) }

    val retryClickListener = View.OnClickListener {
        savedStateHandle.get<Int>(POST_ID_KEY)?.let {
            loadComments(it)
        }
    }

    fun loadComments(postId: Int) {
        savedStateHandle.set(POST_ID_KEY, postId)
        repository.getComments(postId, LoadCommentsCallback())
    }

    private inner class LoadCommentsCallback : SingleObserver<List<Comment>> {
        override fun onSubscribe(d: Disposable) {
            showLoading.set(View.VISIBLE)
            layoutVisibility.set(View.GONE)
        }

        override fun onSuccess(t: List<Comment>) {
            showLoading.set(View.GONE)
            layoutVisibility.set(View.VISIBLE)
            noConnVisibility.set(View.GONE)
            comments.addAll(t.take(3))
        }

        override fun onError(e: Throwable) {
            showLoading.set(View.GONE)
            noConnVisibility.set(View.VISIBLE)
            layoutVisibility.set(View.GONE)
        }
    }
}