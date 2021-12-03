package com.example.movieflix.presentation.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.databinding.ItemVideoListBinding
import javax.inject.Inject

class MovieVideoAdapter @Inject constructor()
    :RecyclerView.Adapter<MovieVideoAdapter.MovieVideoViewHolder>() {

    class MovieVideoViewHolder(private val binding: ItemVideoListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(video: Video){
            binding.txtVideoTitle.text = video.name
            val posterURL = "https://image.tmdb.org/t/p/w500"+video.backdropPath
            Glide.with(binding.imgMovie.context)
                .load(posterURL)
                .into(binding.imgMovie)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Video>(){
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var videos : List<Video>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder {
        return MovieVideoViewHolder(
            ItemVideoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieVideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let{ click ->
                click(video)
            }
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    private var onItemClickListener: ((Video) -> Unit)? = null

    fun setOnItemClickListener(listener: (Video) -> Unit) {
        onItemClickListener = listener
    }
}