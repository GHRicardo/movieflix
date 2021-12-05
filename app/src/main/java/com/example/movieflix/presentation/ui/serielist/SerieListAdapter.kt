package com.example.movieflix.presentation.ui.serielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.databinding.ItemHeaderMovieListBinding
import com.example.movieflix.databinding.ItemMovieListBinding
import javax.inject.Inject

class SerieListAdapter @Inject constructor():
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val ITEM_HEADER = 0
        const val ITEM_SERIE = 1
    }

    class SerieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: Serie) {
            binding.txtMovieTitle.text = serie.name
            val posterURL = "https://image.tmdb.org/t/p/w500" + serie.backdropPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    class SerieHeaderViewHolder(private val binding: ItemHeaderMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: Serie) {
            binding.txtMovieTitle.text = serie.name
            val posterURL = "https://image.tmdb.org/t/p/w500" + serie.posterPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Serie>() {
        override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem.id == newItem.id && oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var series: List<Serie>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_SERIE) {
            return SerieViewHolder(
                ItemMovieListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            return SerieHeaderViewHolder(
                ItemHeaderMovieListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val serie = series[position]
        if(holder.itemViewType == ITEM_SERIE){
            (holder as SerieViewHolder).bind(serie)
        }else{
            (holder as SerieHeaderViewHolder).bind(serie)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click->
                click(serie)
            }
        }
    }

    override fun getItemCount(): Int {
        return series.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            ITEM_HEADER
        }else{
            ITEM_SERIE
        }
    }

    private var onItemClickListener: ((Serie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Serie) -> Unit) {
        onItemClickListener = listener
    }
}