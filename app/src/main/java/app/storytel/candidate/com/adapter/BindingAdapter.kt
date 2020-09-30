package app.storytel.candidate.com.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import app.storytel.candidate.com.R
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    with(view) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.baseline_image_black_18)
            .into(this)
    }
}
