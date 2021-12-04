package com.example.movieflix.presentation.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.databinding.ItemHeaderMovieListBinding
import com.example.movieflix.databinding.ItemMovieListBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor():
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val ITEM_HEADER = 0
        const val ITEM_MOVIE = 1
    }

    class MovieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.txtMovieTitle.text = movie.title
            val posterURL = "https://image.tmdb.org/t/p/w500" + movie.backdropPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    class MovieHeaderViewHolder(private val binding: ItemHeaderMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.txtMovieTitle.text = movie.title
            val posterURL = "https://image.tmdb.org/t/p/w500" + movie.posterPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id && oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var movies: List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_MOVIE) {
            return MovieViewHolder(
                ItemMovieListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            return MovieHeaderViewHolder(
                ItemHeaderMovieListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        if(holder.itemViewType == ITEM_MOVIE){
            (holder as MovieViewHolder).bind(movie)
        }else{
            (holder as MovieHeaderViewHolder).bind(movie)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click->
                click(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            ITEM_HEADER
        }else{
            ITEM_MOVIE
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }
}