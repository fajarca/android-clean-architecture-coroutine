package io.fajarca.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import io.fajarca.core.MarvelApp
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeBinding
import io.fajarca.home.di.DaggerCharacterListComponent
import io.fajarca.home.domain.entities.News
import io.fajarca.home.presentation.adapter.NewsRecyclerAdapter
import io.fajarca.home.presentation.adapter.TopHeadlinePagerAdapter
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    NewsRecyclerAdapter.NewsClickListener {

    @Inject
    lateinit var factory: HomeViewModel.Factory
    private lateinit var adapter: NewsRecyclerAdapter
    override val vm: HomeViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_home
    private lateinit var viewPager : ViewPager
    private lateinit var topHeadlinePagerAdapter: TopHeadlinePagerAdapter

    override fun initDaggerComponent() {
        DaggerCharacterListComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initTopHeadline()

        vm.getNews()
        vm.getTopHeadlines()

        vm.news.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.topHeadlines.observe(viewLifecycleOwner, Observer { subscribeTopHeadlines(it) })
    }

    private fun subscribeTopHeadlines(it: HomeViewModel.TopHeadlinesState<List<News>>) {
        when (it) {
            is HomeViewModel.TopHeadlinesState.Loading -> {
                showLoading(true)
                binding.uiStateView.showLoading()
            }
            is HomeViewModel.TopHeadlinesState.Empty -> {
                showLoading(false)
                binding.uiStateView.showEmptyData()
            }
            is HomeViewModel.TopHeadlinesState.Success -> {
                showLoading(false)
                binding.uiStateView.dismiss()
                refreshTopHeadline(it.data)
            }
        }
    }
    private fun subscribeNews(it: HomeViewModel.TopHeadlinesState<List<News>>) {
        when (it) {
            is HomeViewModel.TopHeadlinesState.Loading -> {
                showLoading(true)
                binding.uiStateView.showLoading()
            }
            is HomeViewModel.TopHeadlinesState.Empty -> {
                showLoading(false)
                binding.uiStateView.showEmptyData()
            }
            is HomeViewModel.TopHeadlinesState.Success -> {
                showLoading(false)
                binding.uiStateView.dismiss()
                refreshNews(it.data)
            }
        }
    }


    private fun initRecyclerView() {
        adapter = NewsRecyclerAdapter(this)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
    }

    private fun refreshTopHeadline(headlines: List<News>) {
        topHeadlinePagerAdapter.refreshHeadlines(headlines)
    }

    private fun refreshNews(data: List<News>) {
        adapter.submitList(data)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }


    private fun initTopHeadline() {
        viewPager = binding.contentTopHeadline.viewpager
        val tabLayout = binding.contentTopHeadline.tabLayout

        topHeadlinePagerAdapter =
            TopHeadlinePagerAdapter(
                ArrayList(),
                requireActivity()
            )
        viewPager.adapter = topHeadlinePagerAdapter
        viewPager.clipToPadding = false
        viewPager.pageMargin = 24
        viewPager.setPadding(48, 8, 48, 8)
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onNewsPressed(news: News) {

    }

}
