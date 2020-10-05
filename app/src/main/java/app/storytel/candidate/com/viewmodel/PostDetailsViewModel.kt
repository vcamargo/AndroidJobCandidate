package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.fragment.PostDetailsFragmentArgs
import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class PostDetailsViewModel(
    private val repository: IRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val POST_ID_KEY = "POST_ID_KEY"
    }

    var postBody = MutableLiveData<String>()
    var postImageUrl = MutableLiveData<String>()
    val commentsLiveData: MutableLiveData<List<Comment>> by lazy {
        MutableLiveData<List<Comment>>()
    }
    val loadingVisibility = MutableLiveData(View.VISIBLE)
    val noConnVisibility = MutableLiveData(View.GONE)
    val detailsLayoutVisibility = MutableLiveData(View.GONE)
    val retryClickListener = View.OnClickListener {
        savedStateHandle.get<Int>(POST_ID_KEY)?.let {
            loadComments(it)
        }
    }

    private val subscriber = object : SingleObserver<List<Comment>> {
        override fun onSubscribe(d: Disposable) {
            loadingVisibility.postValue(View.VISIBLE)
        }

        override fun onSuccess(t: List<Comment>) {
            commentsLiveData.postValue(t)

            loadingVisibility.postValue(View.GONE)
            detailsLayoutVisibility.postValue(View.VISIBLE)
            noConnVisibility.postValue(View.GONE)
        }

        override fun onError(e: Throwable) {
            // If it's not an HttpException means that it was a network error
            if (e !is HttpException) {
                noConnVisibility.postValue(View.VISIBLE)
                detailsLayoutVisibility.postValue(View.GONE)
            }
            //TODO: If it's a HTTP exception like HTTP 500, we'll display a blank screen
            // with no option to retry.
            loadingVisibility.postValue(View.GONE)
        }
    }

    private fun loadComments(postId: Int) {
            repository.getComments(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }

    fun setArgs(args: PostDetailsFragmentArgs) {
        postBody.postValue(args.postBody)
        postImageUrl.postValue(args.postImageUrl)

        savedStateHandle.set(POST_ID_KEY, args.postId)
        loadComments(args.postId)
    }
}