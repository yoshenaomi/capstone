package com.dicoding.capstone.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.model.NewsModel

class ListNewsAdapter(private val listNews :List<NewsModel>) : RecyclerView.Adapter<ListNewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tittleNewsTextView : TextView = itemView.findViewById(R.id.titleNews)
        private val descNewsTextView : TextView = itemView.findViewById(R.id.descNews)
        private val imagesNews : ImageView = itemView.findViewById(R.id.imagesViewNews)

        fun bind(newsItem : NewsModel){
            Glide.with(itemView)
                .load(newsItem.images)
                .into(imagesNews)
            descNewsTextView.text = newsItem.description
            tittleNewsTextView.text = newsItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItems = listNews[position]
        holder.bind(newsItems)
    }
}