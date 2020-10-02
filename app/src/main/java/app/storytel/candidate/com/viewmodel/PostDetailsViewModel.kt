package app.storytel.candidate.com.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.fragment.PostDetailsFragmentArgs
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class PostDetailsViewModel(
    private val repository: IRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val LOG_TAG = "PostDetailsViewModel"
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

    private val disposable = CompositeDisposable()

    fun loadComments(postId: Int) {
        savedStateHandle.set(POST_ID_KEY, postId)

        disposable.add(
            repository.getComments(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(LoadCommentsCallback())
        )
    }

    fun setArgs(args: PostDetailsFragmentArgs) {
        postBody.set(args.postBody)
        postImageUrl.set(args.postImageUrl)
    }

    private inner class LoadCommentsCallback : DisposableSingleObserver<List<Comment>>() {
        override fun onStart() {
            showLoading.set(View.VISIBLE)
            layoutVisibility.set(View.GONE)
        }

        override fun onSuccess(t: List<Comment>) {
            showLoading.set(View.GONE)
            layoutVisibility.set(View.VISIBLE)
            noConnVisibility.set(View.GONE)
            Log.d(LOG_TAG, t.size.toString())
            comments.addAll(t)
        }

        override fun onError(e: Throwable) {
            showLoading.set(View.GONE)
            noConnVisibility.set(View.VISIBLE)
            layoutVisibility.set(View.GONE)
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.dispose()
    }
}