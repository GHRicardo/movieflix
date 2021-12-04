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

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

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
                R.id.movieDetailActivity,
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
                    setupViewsLoading()
                }

                Status.SUCCESS -> {
                    setupViewsNotLoading()
                    it.data?.let{ movieList ->
                        displayMovies(movieList)
                    }
                }

                Status.ERROR -> {
                    setupViewsNotLoading()
                    showErrorMessage(it.message?: "Unknown error")
                }
            }
        }
    }

    private fun setupViewsLoading(){
        with(binding){
            prgSearchMovies.showView()
            lytBtnClose.hideView()
            etxtSearch.disabled()
        }
    }

    private fun setupViewsNotLoading(){
        with(binding){
            prgSearchMovies.hideView()
            lytBtnClose.showView()
            etxtSearch.enabled()
        }
    }

    private fun displayMovies(movies:List<Movie>){
        movieSearchAdapter.movies = movies
        if(movies.isEmpty()){
            binding.txtNoData.showView()
            binding.txtNoData.text = getString(R.string.no_search_data)
        }else{
            binding.txtNoData.hideView()
            binding.rvSearchMovies.showView()
        }
    }

    private fun showErrorMessage(message:String){
        with(binding){
            txtNoData.showView()
            txtNoData.text = message
            rvSearchMovies.hideView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}