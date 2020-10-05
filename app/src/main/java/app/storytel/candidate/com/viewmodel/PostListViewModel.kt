package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class PostListViewModel(
    private val repository: IRepository
) : ViewModel() {

    private val posts: MutableLiveData<List<PostAndPhoto>> by lazy {
        MutableLiveData<List<PostAndPhoto>>().also {
            getPostsAndPhotos()
        }
    }
    val loadingVisibility = MutableLiveData(View.GONE)
    val noConnVisibility = MutableLiveData(View.GONE)
    val listLayoutVisibility = MutableLiveData(View.GONE)
    val retryClickListener = View.OnClickListener {
        getPostsAndPhotos()
    }

    private val subscriber = object : SingleObserver<List<PostAndPhoto>> {
        override fun onSubscribe(d: Disposable) {
            loadingVisibility.postValue(View.VISIBLE)
        }

        override fun onSuccess(t: List<PostAndPhoto>) {
            posts.postValue(t)

            loadingVisibility.postValue(View.GONE)
            listLayoutVisibility.postValue(View.VISIBLE)
            noConnVisibility.postValue(View.GONE)
        }

        override fun onError(e: Throwable) {
            // If it's not an HttpException means that it was a network error
            if (e !is HttpException) {
                noConnVisibility.postValue(View.VISIBLE)
                listLayoutVisibility.postValue(View.GONE)
            }
            //TODO: If it's a HTTP exception like HTTP 500, we'll display a blank screen
            // with no option to retry.
            loadingVisibility.postValue(View.GONE)
        }
    }

    fun getPostsAndPhotosLiveData(): LiveData<List<PostAndPhoto>> {
        return posts
    }

    private fun getPostsAndPhotos() {
        repository.getPostAndPhoto()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
    }
}