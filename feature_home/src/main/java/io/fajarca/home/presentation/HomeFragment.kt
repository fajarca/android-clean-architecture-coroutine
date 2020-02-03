package io.fajarca.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import io.fajarca.core.MarvelApp
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeBinding
import io.fajarca.home.di.DaggerCharacterListComponent
import io.fajarca.home.domain.entities.News
import io.fajarca.home.presentation.adapter.NewsRecyclerAdapter
import io.fajarca.home.presentation.adapter.TopHeadlinePagerAdapter
import io.fajarca.presentation.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    NewsRecyclerAdapter.NewsClickListener {

    @Inject
    lateinit var factory: HomeViewModel.Factory
    private lateinit var adapter: NewsRecyclerAdapter
    override val vm: HomeViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_home
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

        vm.getTopHeadlines()

        vm.newsSource.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.newsSourceState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
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
    private fun subscribeNewsState(it: NewsDataSource.State) {
        when (it) {
            is NewsDataSource.State.Loading -> {
                Timber.v("Loading")
            }
            is NewsDataSource.State.Empty -> {
                Timber.v("Empty")
            }
            is NewsDataSource.State.Success -> {
                Timber.v("Success")
            }
            is NewsDataSource.State.Error -> {
                Timber.v("Error")
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

    private fun subscribeNews(data: PagedList<NewsEntity>) {
        showLoading(false)
        adapter.submitList(data)
        Timber.v("Pagedlist size : ${data.size}")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }


    override fun onNewsPressed(news: NewsEntity) {

    }

}
