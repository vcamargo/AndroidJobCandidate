package app.storytel.candidate.com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.Post
import app.storytel.candidate.com.repository.IRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class PostListViewModel(
    private val repository: IRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    private val photos: MutableLiveData<List<Photo>> by lazy {
        MutableLiveData<List<Photo>>().also {
            loadPhotos()
        }
    }

    fun getPosts() : LiveData<List<Post>> {
        return posts
    }

    fun getPhotos() : LiveData<List<Photo>> {
        return photos
    }

    fun loadPosts() {
        repository.getPosts(LoadPostsCallback())
    }

    fun loadPhotos() {
        repository.getPhotos(LoadPhotosCallback())
    }

    private inner class LoadPostsCallback : SingleObserver<List<Post>> {
        override fun onSubscribe(d: Disposable) {
            //showLoading.set(View.VISIBLE)
        }

        override fun onSuccess(t: List<Post>) {
            posts.postValue(t)
            //showLoading.set(View.GONE)
            //noConnVisibility.set(View.GONE)
            //resErrorVisibility.set(View.GONE)
        }

        override fun onError(e: Throwable) {
            // If it's an HttpException means that there was some issue in the backend
            if (e is HttpException) {
                //resErrorVisibility.set((View.VISIBLE))
            } else {
                // If it's not an HttpException means that it was a network error
                //noConnVisibility.set(View.VISIBLE)
            }
            //showLoading.set(View.GONE)
        }
    }

    private inner class LoadPhotosCallback : SingleObserver<List<Photo>> {
        override fun onSubscribe(d: Disposable) {
            TODO("Not yet implemented")
        }

        override fun onSuccess(t: List<Photo>) {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }
    }
}