package com.example.movieflix.presentation.ui.seriesearch

import android.annotation.SuppressLint
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
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.databinding.FragmentMovieSearchBinding
import com.example.movieflix.other.*
import com.example.movieflix.other.Constants.KEY_SERIE
import com.example.movieflix.other.Constants.SEARCH_EDIT_TIME_DELAY
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SerieSearchFragment : Fragment(){

    @Inject
    lateinit var serieSearchViewModelFactory: SerieSearchViewModelFactory

    @Inject
    lateinit var serieSearchAdapter: SerieSearchAdapter

    private lateinit var serieSearchViewModel: SerieSearchViewModel

    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val TAG = "SeriesSearchFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MovieFlixApplication)
            .createSerieSubComponent()
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etxtSearch.hint = "Nombre de la serie"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        serieSearchViewModel =
            ViewModelProvider(this, serieSearchViewModelFactory)[SerieSearchViewModel::class.java]

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
                        serieSearchViewModel.searchSeriesByName(editable.toString())
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        serieSearchAdapter.setOnItemClickListener { serie ->
            findNavController().navigate(
                R.id.serieDetailActivity,
                bundleOf(KEY_SERIE to serie)
            )
        }
        with(binding.rvSearchMovies){
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = serieSearchAdapter
        }
    }

    private fun setupObservers(){
        serieSearchViewModel.searchedSeriesLiveData.observe(viewLifecycleOwner){
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
                    it.data?.let{ serieList ->
                        displaySeries(serieList)
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

    private fun displaySeries(series:List<Serie>){
        serieSearchAdapter.series = series
        if(series.isEmpty()){
            binding.txtNoData.showView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}