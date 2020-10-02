package app.storytel.candidate.com.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import app.storytel.candidate.com.repository.IRepository

class ViewModelFactory(
    private val repository: IRepository,
    owner: SavedStateRegistryOwner
)
    : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            return PostListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(PostDetailsViewModel::class.java)) {
                return PostDetailsViewModel(repository, handle) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}