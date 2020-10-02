package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.storytel.candidate.com.R
import app.storytel.candidate.com.adapter.PostListAdapter
import app.storytel.candidate.com.databinding.FragmentPostListBinding
import app.storytel.candidate.com.repository.Repository
import app.storytel.candidate.com.viewmodel.PostListViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory
import app.storytel.candidate.com.webservice.Webservice

class PostListFragment : Fragment() {

    companion object {
        val LOG_TAG = "PostList"
    }

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
            val vm = ViewModelProvider (
                this,
                ViewModelFactory (
                    Repository(Webservice.create()),
                    this)
            ).get(PostListViewModel::class.java)

            binding.adapter = adapter
            binding.viewmodel = vm

            vm.getPosts().observe(viewLifecycleOwner, { posts ->
                adapter.submitList(posts)
                adapter.notifyDataSetChanged()
            })

        } catch (ex: IllegalArgumentException) {
            Log.wtf(LOG_TAG, ex)
        }
    }
}