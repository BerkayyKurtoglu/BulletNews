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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.adapter.HomeFragmentMainRecyclerViewAdapter
import com.example.bulletnewsoriginal.adapter.AdapterListener
import com.example.bulletnewsoriginal.adapter.ViewPagerAdapterForHomeFragment
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.util.SharedPreferenceService
import com.example.bulletnewsoriginal.viewModel.MainFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var viewPagerAdapter :ViewPagerAdapterForHomeFragment
    private lateinit var viewPager2: ViewPager2
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var mainRecyclerViewAdapter: HomeFragmentMainRecyclerViewAdapter
    private lateinit var sharedPreferenceService: SharedPreferenceService
    private lateinit var mainFragmentViewModel : MainFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        operateSystemUI()
        operateNestedScrolling()
        sharedPreferenceService = SharedPreferenceService(requireContext())
        controlDarkMode()

        viewPager2 = homeFragment_ViewPager2
        operateViewPager()

        floatingActionButton = (activity as AppCompatActivity).activity_FAB
        floatingActionButton.setOnClickListener {FABListener(it)}

        homeFragment_main_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainRecyclerViewAdapter = HomeFragmentMainRecyclerViewAdapter(arrayListOf(),requireContext())

        mainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        mainFragmentViewModel.getTotalNews(requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

        homeFragment_swipeRefreshLayout.setOnRefreshListener {
            mainFragmentViewModel.getTotalNews(requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            homeFragment_swipeRefreshLayout.isRefreshing = false
        }

        //topAppBarMenuClickListener()

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

        mainFragmentViewModel.topHeadLinesLiveData.observe(viewLifecycleOwner){
            it?.let {
                viewPagerAdapter = ViewPagerAdapterForHomeFragment(requireContext(),homeFragment_topHeadlines_seeAllText,it)
                viewPager2.adapter = viewPagerAdapter
            }
        }

        mainFragmentViewModel.loadingStatuLiveData.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    homeFragment_progressBar.visibility = View.VISIBLE
                    homeFragment_total_screen.visibility = View.INVISIBLE
                }else{
                    homeFragment_progressBar.visibility = View.INVISIBLE
                    homeFragment_total_screen.visibility = View.VISIBLE
                }
            }
        }
        mainFragmentViewModel.errorStatuLiveData.observe(viewLifecycleOwner){

            it?.let {
                if (it){
                    println("Error")
                    homeFragment_total_screen.visibility = View.INVISIBLE
                }
            }

        }

        mainFragmentViewModel.subNewsLiveData.observe(viewLifecycleOwner){
            it?.let {
                println(it.size)
                mainRecyclerViewAdapter.replaceAdapterWithNewArray(it)
                homeFragment_main_recyclerView.adapter = mainRecyclerViewAdapter
            }
        }
    }

    private fun FABListener(view: View){
        val extras = FragmentNavigatorExtras(view to "container_transition")
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment2())
    }

    private fun operateNestedScrolling(){
        val nestedScroll = homeFragment_nested_scroll_view
        nestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (scrollY > oldScrollY) {
                //Scroll Down
                (activity as AppCompatActivity).activity_FAB.hide()
            }
            if (scrollY < oldScrollY) {
                //Scroll Up
                (activity as AppCompatActivity).activity_FAB.show()
            }

            if (scrollY == 0) {
                //Top Scroll
            }

        }
    }

    private fun operateSystemUI(){
        (activity as AppCompatActivity).activity_appbar.apply {
            this.visibility = View.VISIBLE
            this.elevation = 5f
        }
        (activity as AppCompatActivity).activity_toolbar.title = "News From World"
        (activity as AppCompatActivity).activity_bottom_menu.visibility = View.VISIBLE
        (activity as AppCompatActivity).activity_FAB.show()
    }

    private fun operateViewPager(){
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val float = 1 - abs(position)
            page.scaleY = 0.8f + float * 0.2f

        })
        viewPager2.setPageTransformer(compositePageTransformer)
    }



}