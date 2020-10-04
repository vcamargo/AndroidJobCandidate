package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
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
    private val disposable = CompositeDisposable()

    val loadingVisibility = MutableLiveData(View.GONE)
    val noConnVisibility = MutableLiveData(View.GONE)
    val listLayoutVisibility = MutableLiveData(View.GONE)

    val retryClickListener = View.OnClickListener {
        getPostsAndPhotos()
    }

    fun getPostsAndPhotosLiveData(): LiveData<List<PostAndPhoto>> {
        return posts
    }

    private fun getPostsAndPhotos() {
        disposable.add(
            repository.getPostAndPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostAndPhoto>>() {
                    override fun onStart() {
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
                        loadingVisibility.postValue(View.GONE)
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposable.dispose()
    }
}