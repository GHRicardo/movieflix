package com.example.movieflix.presentation.ui.moviedetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.databinding.FragmentMovieDetailBinding
import com.example.movieflix.other.Constants.KEY_MOVIE
import com.example.movieflix.other.Status
import com.example.movieflix.other.animateShow
import com.example.movieflix.other.hideView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import javax.inject.Inject

class MovieDetailFragment : Fragment() {
    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModelFactory

    @Inject
    lateinit var movieVideoAdapter: MovieVideoAdapter

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var movie:Movie

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieFlixApplication)
            .createMovieSubcomponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(KEY_MOVIE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel =
            ViewModelProvider(this, movieDetailViewModelFactory)[MovieDetailViewModel::class.java]
        setupRecyclerView()
        setupYoutubePlayer()
        setupObservers()
        movieDetailViewModel.getVideosFromMovie(movie.id)
        bindMovie(movie)
    }

    private fun setupRecyclerView(){
        movieVideoAdapter.setOnItemClickListener { video ->
            playYoutubeVideo(video.key)
        }
        with(binding.rvVideosFromMovie){
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            adapter = movieVideoAdapter
        }
    }

    private fun setupYoutubePlayer(){
        lifecycle.addObserver(binding.youtubeVideoView)
    }

    private fun setupObservers(){
        movieDetailViewModel.videosFromMovieLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    /* NO-OP*/
                }

                Status.SUCCESS -> {
                    it.data?.let{ videoList ->
                        if(videoList.isNotEmpty()){
                            displayVideos(videoList.map { video ->
                                video.apply {
                                    backdropPath = movie.backdropPath
                                }
                            })
                        }
                    }
                }

                Status.ERROR -> {
                    /* NO-OP*/
                }
            }
        }
    }

    private fun displayVideos(videos:List<Video>){
        movieVideoAdapter.videos = videos
        binding.lytRvVideos.animateShow(binding.root, false)
        binding.imgPlaceholderMovie.hideView()
        playYoutubeVideo(videos[0].key)
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovie(movie: Movie){
        with(binding){
            txtMovieTitle.text = movie.title
            txtReleaseDate.text = movie.releaseDate
            txtVotes.text = "${movie.voteCount} votos"
            txtOverview.text = movie.overview
            val posterURL = "https://image.tmdb.org/t/p/w500" + movie.backdropPath
            Glide.with(this@MovieDetailFragment)
                .load(posterURL)
                .into(binding.imgPlaceholderMovie)
        }
    }

    private fun playYoutubeVideo(videoKey:String){
        binding.youtubeVideoView.getYouTubePlayerWhenReady(object:YouTubePlayerCallback{
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoKey, 0F)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}