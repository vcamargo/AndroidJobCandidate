package app.storytel.candidate.com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.BR
import app.storytel.candidate.com.databinding.ListItemPostBinding
import app.storytel.candidate.com.fragment.PostListFragmentDirections
import app.storytel.candidate.com.model.PostAndPhoto

class PostListAdapter : ListAdapter<PostAndPhoto, RecyclerView.ViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class PostViewHolder(
        val binding: ListItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.post?.let { post ->
                    navigateToPost(post, it)
                }
            }
        }

        private fun navigateToPost(
            post: PostAndPhoto,
            view: View
        ) {
            val direction =
                PostListFragmentDirections.actionPostListFragmentToPostDetailsFragment(
                    post.id,
                    post.thumbnailUrl,
                    post.body
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: PostAndPhoto) {
            binding.apply {
                setVariable(BR.post, item)
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        (holder as? PostViewHolder)?.bind(post)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as? PostViewHolder)?.binding?.let {
            it.post = null
            it.executePendingBindings()
        }

        super.onViewRecycled(holder)
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostAndPhoto>() {

    override fun areItemsTheSame(oldItem: PostAndPhoto, newItem: PostAndPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostAndPhoto, newItem: PostAndPhoto): Boolean {
        return oldItem == newItem
    }
}