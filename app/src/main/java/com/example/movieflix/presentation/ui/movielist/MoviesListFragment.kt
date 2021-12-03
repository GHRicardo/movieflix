package com.example.movieflix.presentation.ui.movielist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.R
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.databinding.FragmentMoviesListBinding
import com.example.movieflix.other.Constants.KEY_MOVIE
import com.example.movieflix.other.Constants.OPTION_MOVIES
import com.example.movieflix.other.Status
import com.example.movieflix.other.hideView
import com.example.movieflix.other.showView
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class MoviesListFragment : Fragment() {

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory

    @Inject
    lateinit var movieAdapter: MovieAdapter

    private lateinit var movieListViewModel: MovieListViewModel

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private var option = ""

    companion object{
        const val TAG = "MoviesListFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieFlixApplication)
            .createMovieSubcomponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            option = it.getString(OPTION_MOVIES, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListViewModel =
            ViewModelProvider(this, movieListViewModelFactory)[MovieListViewModel::class.java]
        setupRecyclerView()
        setupObservers()
        movieListViewModel.getPopularMovies()

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    movieListViewModel.getPopularMovies()
                }else{
                    movieListViewModel.getTopRatedMovies()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun setupRecyclerView(){
        movieAdapter.setOnItemClickListener { movie ->
            findNavController().navigate(
                R.id.action_moviesListFragment_to_movieDetailFragment,
                bundleOf(KEY_MOVIE to movie)
            )
        }
        with(binding.rvMovies){
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = movieAdapter
        }
    }

    private fun setupObservers(){
        movieListViewModel.moviesLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.prgMovies.showView()
                }

                Status.SUCCESS -> {
                    binding.prgMovies.hideView()
                    it.data?.let { movieList ->
                        displayMovies(movieList)
                    }
                }

                Status.ERROR -> {
                    binding.prgMovies.hideView()
                    showErrorMessage(it.message?:"Unknown error")
                }
            }
        }
    }

    private fun displayMovies(movies:List<Movie>){
        movieAdapter.movies = movies
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    private fun showErrorMessage(message:String){
        Log.e(TAG, "Error: $message")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}