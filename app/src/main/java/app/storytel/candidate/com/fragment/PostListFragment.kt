package app.storytel.candidate.com.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import app.storytel.candidate.com.R
import app.storytel.candidate.com.adapter.PostListAdapter
import app.storytel.candidate.com.databinding.FragmentPostListBinding
import app.storytel.candidate.com.repository.Repository
import app.storytel.candidate.com.viewmodel.PostListViewModel
import app.storytel.candidate.com.viewmodel.ViewModelFactory

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
        return binding.root
    }

    private fun subscribeUi(adapter: PostListAdapter, binding: FragmentPostListBinding) {
        try {
            val vm = ViewModelProvider (
                this,
                ViewModelFactory (
                    Repository(),
                    this)
            ).get(PostListViewModel::class.java)

            binding.postsList.adapter = adapter
            binding.viewmodel = vm

            vm.getPosts().observe(viewLifecycleOwner, { posts ->
                adapter.submitList(posts)
            })

        } catch (ex: IllegalArgumentException) {
            Log.wtf(LOG_TAG, ex)
        }
    }
}