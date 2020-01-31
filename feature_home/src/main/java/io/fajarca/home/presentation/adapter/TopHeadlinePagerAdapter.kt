package io.fajarca.home.presentation.adapter

import io.fajarca.home.R
import io.fajarca.home.domain.entities.TopHeadline
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class TopHeadlinePagerAdapter(var headlines : List<TopHeadline>, val context : Context) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj : Any): Boolean {
        return view == obj
    }

    override fun getCount() = headlines.size

    override fun destroyItem(container: ViewGroup, position: Int, obj : Any) {
        val view = obj as View
        container.removeView(view)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_top_headline, container, false)

        val imageView =     view.findViewById<ImageView>(R.id.ivHeadline)
        val tvTitle = view.findViewById<TextView>(R.id.tvHeadlineTitle)

        val headline = headlines[position].imageUrl

        val options = RequestOptions
            .fitCenterTransform()


        Glide.with(context)
            .load(headline)
            .apply(options)
            .thumbnail(0.1f)
            .into(imageView)

        tvTitle.text = headlines[position].title


        container.addView(view)

        return view
    }

    fun refreshHeadlines(headlines : List<TopHeadline>) {
        this.headlines = headlines
        notifyDataSetChanged()
    }
}