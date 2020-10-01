package app.storytel.candidate.com.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import app.storytel.candidate.com.R

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
