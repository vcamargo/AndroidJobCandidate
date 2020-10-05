package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.storytel.candidate.com.R
import app.storytel.candidate.com.adapter.PostListAdapter
import app.storytel.candidate.com.databinding.FragmentPostListBinding
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.viewmodel.PostListViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostListFragment : Fragment() {

    companion object {
        val LOG_TAG = "PostList"
    }

    @Inject
    lateinit var repository: IRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(PostListAdapter(), binding)

        // Show Fragment Title in Toolbar
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.root.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        return binding.root
    }

    private fun subscribeUi(adapter: PostListAdapter, binding: FragmentPostListBinding) {
        try {
            val vm = ViewModelProvider(
                this,
                ViewModelFactory(
                    repository,
                    this
                )
            ).get(PostListViewModel::class.java)

            binding.adapter = adapter
            binding.viewmodel = vm
            binding.lifecycleOwner = this

        } catch (ex: IllegalArgumentException) {
            Log.wtf(LOG_TAG, ex)
        }
    }
}