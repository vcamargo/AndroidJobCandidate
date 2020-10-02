package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

class PostListViewModel(
    private val repository: IRepository
) : ViewModel() {

    private val posts: MutableLiveData<List<PostAndPhoto>> by lazy {
        MutableLiveData<List<PostAndPhoto>>().also {
            getPostsAndPhotos()
        }
    }
    private val disposable = CompositeDisposable()

    val showLoading = ObservableInt().also { it.set(View.GONE) }
    val noConnVisibility = ObservableInt().also { it.set(View.GONE) }
    val layoutVisibility = ObservableInt().also { it.set(View.GONE) }

    val retryClickListener = View.OnClickListener {
        getPostsAndPhotos()
    }

    fun getPosts(): LiveData<List<PostAndPhoto>> {
        return posts
    }

    private fun getPostsAndPhotos() {
        disposable.add(
            repository.getPostAndPhoto()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(loadPostsCallbackprivate)
        )
    }

    private val loadPostsCallbackprivate = object : DisposableSingleObserver<List<PostAndPhoto>>() {
        override fun onStart() {
            showLoading.set(View.VISIBLE)
        }

        override fun onSuccess(t: List<PostAndPhoto>) {
            posts.postValue(t)

            showLoading.set(View.GONE)
            layoutVisibility.set(View.VISIBLE)
            noConnVisibility.set(View.GONE)
        }

        override fun onError(e: Throwable) {
            // If it's not an HttpException means that it was a network error
            if (e !is HttpException) {
                noConnVisibility.set(View.VISIBLE)
                layoutVisibility.set(View.GONE)
            }
            showLoading.set(View.GONE)
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.dispose()
    }
}