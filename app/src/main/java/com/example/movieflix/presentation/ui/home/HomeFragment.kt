package com.example.movieflix.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.bumptech.glide.Glide
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.R
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.databinding.FragmentHomeBinding
import com.example.movieflix.other.Constants
import com.example.movieflix.other.Status
import com.example.movieflix.other.hideView
import com.example.movieflix.other.showView
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieSelected:Movie

    companion object{
        const val TAG = "HomeFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieFlixApplication)
            .createHomeSubComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.crdMovieInfo.hideView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        setupObservers()
        homeViewModel.getSomeRandomMovie()
        binding.txtMovies.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_moviesListFragment
            )
        }

        binding.txtSeries.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_seriesListFragment
            )
        }

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_movieSearchFragment
            )
        }

        binding.crdBtnVer.setOnClickListener {
            findNavController().navigate(
                R.id.movieDetailActivity,
                bundleOf(Constants.KEY_MOVIE to movieSelected)
            )
        }
    }

    private fun setupObservers(){
        homeViewModel.randomMovieLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    it.data?.let{ movie ->
                        bindMovie(movie)
                    }
                }

                Status.ERROR ->{
                    Log.e(TAG, "error: ${it.message}")
                }
            }
        }
    }

    private fun bindMovie(movie:Movie){
        movieSelected = movie
        binding.txtMovieTitle.text = movie.title
        binding.txtOverview.text = movie.overview
        val posterURL = "https://image.tmdb.org/t/p/w500"+movie.posterPath
        Glide.with(this)
            .load(posterURL)
            .into(binding.imgHomeMovie)
        binding.imgHomeMovie.showView()
        binding.crdMovieInfo.showView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}