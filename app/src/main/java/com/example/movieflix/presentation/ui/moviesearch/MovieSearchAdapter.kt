package com.example.movieflix.presentation.ui.moviesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.databinding.ItemMovieSearchBinding
import javax.inject.Inject

class MovieSearchAdapter @Inject constructor() :
    RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder>(){
    class MovieViewHolder(private val binding: ItemMovieSearchBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie){
            binding.txtMovieTitle.text = movie.title
            val posterURL = "https://image.tmdb.org/t/p/w500"+movie.posterPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var movies : List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click->
                click(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }
}