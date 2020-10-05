package app.storytel.candidate.com.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import app.storytel.candidate.com.model.PostAndPhoto

import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.baseline_image_black_18)
            .into(view)
    }
}

@BindingAdapter("setAdapter")
fun setAdapter(list: RecyclerView, adapter: PostListAdapter) {
    list.setHasFixedSize(true)
    list.adapter = adapter
}

@BindingAdapter("setData")
fun setData(list: RecyclerView, data: LiveData<List<PostAndPhoto>>) {
    (list.adapter as? PostListAdapter)?.submitList(data.value)
    (list.adapter as? PostListAdapter)?.notifyDataSetChanged()
}
