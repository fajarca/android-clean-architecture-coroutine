package io.fajarca.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import io.fajarca.presentation.R


@BindingAdapter("loadPortraitImage")
fun loadPortraitImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) return

    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .centerCrop()

    Glide.with(view.context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .thumbnail(0.2f)
        .apply(requestOptions)
        .into(view)
}


