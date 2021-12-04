package com.example.movieflix.presentation.ui.serielist

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import com.example.movieflix.MovieFlixApplication
import com.example.movieflix.R
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.databinding.FragmentMoviesListBinding
import com.example.movieflix.other.Constants.KEY_SERIE
import com.example.movieflix.other.Status
import com.example.movieflix.other.animateViewHeaderFling
import com.example.movieflix.other.hideView
import com.example.movieflix.other.showView
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class SeriesListFragment : Fragment() {

    @Inject
    lateinit var serieListViewModelFactory: SerieListViewModelFactory

    @Inject
    lateinit var serieListAdapter: SerieListAdapter

    private lateinit var serieListViewModel: SerieListViewModel

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private var setPoint = 0F
    private var canOpenWithTouch = false
    private var mTotalScrolled = 0

    companion object{
        const val TAG = "SeriesListFragment"
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
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtToolbarTitle.text = "Series"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPoint = resources.getDimension(R.dimen.toolbar_height)
        serieListViewModel =
            ViewModelProvider(this, serieListViewModelFactory)[SerieListViewModel::class.java]
        setupRecyclerView()
        setupObservers()
        serieListViewModel.getPopularSeries()

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_seriesListFragment_to_serieSearchFragment
            )
        }

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    serieListViewModel.getPopularSeries()
                }else{
                    serieListViewModel.getTopRatedSeries()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun setupRecyclerView(){
        serieListAdapter.setOnItemClickListener { serie ->
            findNavController().navigate(
                R.id.serieDetailActivity,
                bundleOf(KEY_SERIE to serie)
            )
        }

        with(binding.rvMovies){
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = serieListAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    mTotalScrolled += dy
                    autoscrollHeader()
                }
            })
        }
    }

    private fun autoscrollHeader() {
        val fTotalScrolled = mTotalScrolled.toFloat()
        val offset = 1 - fTotalScrolled / setPoint
        if (offset >= 0) {
            binding.appBar.translationY = -fTotalScrolled
            canOpenWithTouch = offset == 0f
        } else {
            if (!canOpenWithTouch) {
                val duration: Long = 50
                binding.appBar.animateViewHeaderFling(duration, setPoint, 0f)
            }
            canOpenWithTouch = true
        }
    }

    private fun setupObservers(){
        serieListViewModel.seriesLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.prgMovies.showView()
                }

                Status.SUCCESS -> {
                    binding.prgMovies.hideView()
                    it.data?.let { serieList ->
                        displaySeries(serieList)
                    }
                }

                Status.ERROR -> {
                    binding.prgMovies.hideView()
                    showErrorMessage(it.message?:"Unknown error")
                }
            }
        }
    }

    private fun displaySeries(series:List<Serie>){
        mTotalScrolled = 0
        serieListAdapter.series = series
        binding.txtError.hideView()
        binding.rvMovies.showView()
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
        mTotalScrolled = 0
        with(binding){
            txtError.text = message
            txtError.showView()
            rvMovies.hideView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}