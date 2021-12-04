package com.example.movieflix.presentation.ui.seriedetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.databinding.ActivityMovieDetailBinding
import com.example.movieflix.other.Constants.KEY_SERIE
import com.example.movieflix.other.Status
import com.example.movieflix.other.animateShow
import com.example.movieflix.other.hideView
import com.example.movieflix.presentation.ui.moviedetail.MovieVideoAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import javax.inject.Inject

class SerieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    @Inject
    lateinit var serieDetailViewModelFactory: SerieDetailViewModelFactory

    @Inject
    lateinit var movieVideoAdapter: MovieVideoAdapter

    private lateinit var serieDetailViewModel: SerieDetailViewModel

    private lateinit var serie:Serie

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieFlixApplication)
            .createSerieSubComponent()
            .inject(this)
        super.onCreate(savedInstanceState)

        serie = intent?.extras?.getParcelable(KEY_SERIE)!!

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        serieDetailViewModel =
            ViewModelProvider(this, serieDetailViewModelFactory)[SerieDetailViewModel::class.java]

        setupRecyclerView()
        setupYoutubePlayer()
        setupObservers()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        serieDetailViewModel.getVideosFromSerie(serie.id)
        bindSerie(serie)
    }

    private fun setupRecyclerView(){
        movieVideoAdapter.setOnItemClickListener { video ->
            playYoutubeVideo(video.key)
        }
        with(binding.rvVideosFromMovie){
            layoutManager = LinearLayoutManager(
                this@SerieDetailActivity,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = movieVideoAdapter
        }
    }

    private fun setupYoutubePlayer(){
        lifecycle.addObserver(binding.youtubeVideoView)
    }

    private fun setupObservers(){
        serieDetailViewModel.videosFromSerieLiveData.observe(this){
            when(it.status){
                Status.LOADING -> {
                    /* NO-OP*/
                }

                Status.SUCCESS -> {
                    it.data?.let{ videoList ->
                        if(videoList.isNotEmpty()){
                            displayVideos(videoList.map { video ->
                                video.apply {
                                    backdropPath = serie.backdropPath
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
    private fun bindSerie(serie: Serie){
        with(binding){
            txtMovieTitle.text = serie.name
            txtReleaseDate.text = serie.firstAirDate
            txtVotes.text = "${serie.voteCount} votos"
            txtOverview.text = serie.overview
            val posterURL = "https://image.tmdb.org/t/p/w500" + serie.backdropPath
            Glide.with(this@SerieDetailActivity)
                .load(posterURL)
                .into(binding.imgPlaceholderMovie)
        }
    }

    private fun playYoutubeVideo(videoKey:String){
        binding.youtubeVideoView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoKey, 0F)
            }
        })
    }
}