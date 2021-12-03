package com.example.movieflix.presentation.ui.moviesearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.R
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.databinding.FragmentMovieSearchBinding
import com.example.movieflix.other.*
import com.example.movieflix.other.Constants.KEY_MOVIE
import com.example.movieflix.other.Constants.SEARCH_EDIT_TIME_DELAY
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieSearchFragment : Fragment(){

    @Inject
    lateinit var movieSearchViewModelFactory: MovieSearchViewModelFactory

    @Inject
    lateinit var movieSearchAdapter: MovieSearchAdapter

    private lateinit var movieSearchViewModel: MovieSearchViewModel

    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val TAG = "MoviesSearchFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MovieFlixApplication)
            .createMovieSubcomponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieSearchViewModel =
            ViewModelProvider(this, movieSearchViewModelFactory)[MovieSearchViewModel::class.java]

        setupEditTextBehavior()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupEditTextBehavior(){
        var job: Job? = null
        binding.lytBtnClose.setOnClickListener {
            binding.etxtSearch.text.clear()
        }
        binding.etxtSearch.addTextChangedListener{ editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_EDIT_TIME_DELAY)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        movieSearchViewModel.searchMoviesByName(editable.toString())
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        movieSearchAdapter.setOnItemClickListener { movie ->
            findNavController().navigate(
                R.id.action_movieSearchFragment_to_movieDetailFragment,
                bundleOf(KEY_MOVIE to movie)
            )
        }
        with(binding.rvSearchMovies){
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieSearchAdapter
        }
    }

    private fun setupObservers(){
        movieSearchViewModel.searchedMoviesLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.prgSearchMovies.showView()
                    binding.lytBtnClose.hideView()
                    binding.etxtSearch.disabled()
                }

                Status.SUCCESS -> {
                    binding.prgSearchMovies.hideView()
                    binding.lytBtnClose.showView()
                    binding.etxtSearch.enabled()
                    it.data?.let{ movieList ->
                        displayMovies(movieList)
                    }
                }

                Status.ERROR -> {
                    binding.prgSearchMovies.hideView()
                    binding.lytBtnClose.showView()
                    binding.etxtSearch.enabled()
                }
            }
        }
    }

    private fun displayMovies(movies:List<Movie>){
        movieSearchAdapter.movies = movies
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}