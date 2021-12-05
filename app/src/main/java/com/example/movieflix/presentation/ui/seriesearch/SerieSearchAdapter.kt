package com.example.movieflix.presentation.ui.seriesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.databinding.ItemMovieSearchBinding
import javax.inject.Inject

class SerieSearchAdapter @Inject constructor() :
    RecyclerView.Adapter<SerieSearchAdapter.SerieViewHolder>(){
    class SerieViewHolder(private val binding: ItemMovieSearchBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(serie: Serie){
            binding.txtMovieTitle.text = serie.name
            val posterURL = "https://image.tmdb.org/t/p/w500"+serie.posterPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Serie>(){
        override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var series : List<Serie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        return SerieViewHolder(
            ItemMovieSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = series[position]
        holder.bind(serie)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click->
                click(serie)
            }
        }
    }

    override fun getItemCount(): Int {
        return series.size
    }

    private var onItemClickListener: ((Serie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Serie) -> Unit) {
        onItemClickListener = listener
    }
}