package com.example.bulletnewsoriginal.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.adapter.SearchFragmentPagedAdapter
import com.example.bulletnewsoriginal.adapter.SearchFragmentSearchAdapter
import com.example.bulletnewsoriginal.adapter.ViewPagerAdapterForSearchFragment
import com.example.bulletnewsoriginal.util.SharedPreferenceService
import com.example.bulletnewsoriginal.viewModel.SearchFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_pager_item_search_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    private lateinit var searchFragmentViewModel: SearchFragmentViewModel
    private lateinit var viewPagerAdapterForSearchFragment : ViewPagerAdapterForSearchFragment
    private lateinit var searchFragmentSearchAdapter: SearchFragmentSearchAdapter
    private lateinit var searchFragmentPagedAdapter: SearchFragmentPagedAdapter

    @Inject lateinit var sharedPreferenceService: SharedPreferenceService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchFragmentViewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        operateSystemUI()
        operateTabLayout()
        //sharedPreferenceService = SharedPreferenceService(requireContext())
        controlDarkMode()

        searchFragmentViewModel.getNewsForSearchFragment(requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        searchFragmentSearchAdapter = SearchFragmentSearchAdapter()

        searchFragmentPagedAdapter = SearchFragmentPagedAdapter()
        searchFragmentPagedAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        searchFragment_searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchFragment_searchRecyclerView.adapter = searchFragmentPagedAdapter

        makeSearch(view)

        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun controlDarkMode(){
        if (sharedPreferenceService.controlCheckState("dark_mode") == true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun observeViewModel(){

        lifecycleScope.launch {
            searchFragmentPagedAdapter.loadStateFlow.collectLatest {loadState->
                searchFragment_progressBar.isVisible = loadState.refresh is LoadState.Loading
            }
        }

        searchFragmentViewModel.newsLiveData.observe(viewLifecycleOwner){
            it?.let {
                viewPagerAdapterForSearchFragment.replaceAdapter(it)
            }
        }

        searchFragmentViewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it){
                searchFragment_totalScreen.visibility = View.GONE
                searchFragment_progressBar.visibility = View.VISIBLE
            }else{
                searchFragment_totalScreen.visibility = View.VISIBLE
                searchFragment_progressBar.visibility = View.GONE
            }
        }

        searchFragmentViewModel.searchNewsData.observe(viewLifecycleOwner){
            searchFragmentSearchAdapter.searchNews = it.articles
        }

    }

    private fun operateSystemUI(){
        (activity as AppCompatActivity).activity_appbar.apply {
            this.visibility = View.VISIBLE
            this.elevation = 5f
        }
        (activity as AppCompatActivity).activity_toolbar.title = "Discover"
        (activity as AppCompatActivity).activity_bottom_menu.visibility = View.VISIBLE
        (activity as AppCompatActivity).activity_FAB.hide()

    }

    private fun operateTabLayout(){
        viewPagerAdapterForSearchFragment = ViewPagerAdapterForSearchFragment(ArrayList(),requireContext())
        searchFragment_viewPager.adapter = viewPagerAdapterForSearchFragment
        TabLayoutMediator(searchFragment_tabLayout,searchFragment_viewPager){tab, position->
            when(position){
                0->{
                    tab.text = "Health"
                }
                1->{
                    tab.text = "Politics"
                }
                2->{
                    tab.text = "Art"
                }
                3->{
                    tab.text = "Food"
                }
                4->{
                    tab.text = "Science"
                }
                5->{
                    tab.text = "Covid-19"
                }

            }

        }.attach()

        searchFragment_tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                searchFragment_viewPager_recyclerView.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

    }

    private fun makeSearch(view : View){

        var job : Job? = null

        searchFragment_searchBox.addTextChangedListener {
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            TransitionManager.beginDelayedTransition(view.rootView as ViewGroup, sharedAxis)
            job?.cancel()
            it?.let {
                if (it.toString().isNotEmpty() && it.toString().length >= 3){
                    searchFragment_tabLayout.visibility = View.GONE
                    searchFragment_viewPager_recyclerView.visibility = View.GONE
                    searchFragment_searchRecyclerView.visibility = View.VISIBLE
                }else{
                    searchFragment_tabLayout.visibility = View.VISIBLE
                    searchFragment_viewPager_recyclerView.visibility = View.VISIBLE
                    searchFragment_searchRecyclerView.visibility = View.GONE
                }
            }
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    //searchFragmentViewModel.searchNews(it.toString())
                    val pager = searchFragmentViewModel.searchPagedNews(it.toString())
                    pager.collectLatest {
                        searchFragmentPagedAdapter.submitData(it)
                    }
                }
            }
        }

    }


}