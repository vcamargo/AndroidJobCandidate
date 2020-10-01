package app.storytel.candidate.com.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class PostListViewModel(
    private val repository: IRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val posts: MutableLiveData<List<PostAndPhoto>> by lazy {
        MutableLiveData<List<PostAndPhoto>>().also {
            loadPosts()
        }
    }
    val showLoading = ObservableInt().also { it.set(View.GONE) }
    val noConnVisibility = ObservableInt().also { it.set(View.GONE) }
    val layoutVisibility = ObservableInt().also { it.set(View.GONE) }

    val retryClickListener = View.OnClickListener {
        loadPosts()
    }

    fun getPosts() : LiveData<List<PostAndPhoto>> {
        return posts
    }

    fun loadPosts() {
        repository.getPosts(LoadPostsCallback())
    }

    fun loadPhotos() {
        repository.getPhotos(LoadPhotosCallback())
    }

    private inner class LoadPostsCallback : SingleObserver<List<PostAndPhoto>> {
        override fun onSubscribe(d: Disposable) {
            showLoading.set(View.VISIBLE)
        }

        override fun onSuccess(t: List<PostAndPhoto>) {
            posts.postValue(t)
            loadPhotos()

            showLoading.set(View.GONE)
            layoutVisibility.set(View.VISIBLE)
            noConnVisibility.set(View.GONE)
        }

        override fun onError(e: Throwable) {
            // If it's an HttpException means that there was some issue in the backend
            if (e is HttpException) {
                //resErrorVisibility.set((View.VISIBLE))
            } else {
                // If it's not an HttpException means that it was a network error
                noConnVisibility.set(View.VISIBLE)
                layoutVisibility.set(View.GONE)
            }
            showLoading.set(View.GONE)
        }
    }

    private inner class LoadPhotosCallback : SingleObserver<List<Photo>> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: List<Photo>) {
            val data = posts.value
            data?.let {
                val photos = t.take(it.size)
                it.mapIndexed { index, postAndPhoto ->
                    postAndPhoto.thumbnailUrl = photos[index].thumbnailUrl
                }
            }

            posts.postValue(data)
        }

        override fun onError(e: Throwable) {

        }
    }
}