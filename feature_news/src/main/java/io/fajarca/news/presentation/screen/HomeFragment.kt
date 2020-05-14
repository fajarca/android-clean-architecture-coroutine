package io.fajarca.news.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import io.fajarca.core.BuzzNewsApp
import io.fajarca.core.vo.Result
import io.fajarca.core.vo.UiState
import io.fajarca.navigation.Origin
import io.fajarca.news.R
import io.fajarca.news.databinding.FragmentHomeBinding
import io.fajarca.news.di.DaggerNewsComponent
import io.fajarca.news.domain.entities.News
import io.fajarca.news.presentation.adapter.NewsRecyclerAdapter
import io.fajarca.news.presentation.viewmodel.HomeViewModel
import io.fajarca.presentation.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), NewsRecyclerAdapter.NewsClickListener {

    private val adapter by lazy { NewsRecyclerAdapter(this) }
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.fragmentHome).build() }
    override fun getViewModelClass() = HomeViewModel::class.java
    override fun getLayoutResourceId() = R.layout.fragment_home

    override fun initDaggerComponent() {
        DaggerNewsComponent
            .builder()
            .coreComponent(BuzzNewsApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }

    override fun onPause() {
        binding.shimmer.stop()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        initSwipeRefresh()
        registerObservers()

        vm.setSearchQuery("id", null)
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.includedToolbar.toolbar)
        (binding.includedToolbar.toolbar as Toolbar).setupWithNavController(findNavController(), appBarConfiguration)
    }


    private fun initSwipeRefresh() {
        val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            vm.refreshNews("id", null)
        }
        binding.swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshListener.onRefresh()
    }

    private fun registerObservers() {
        vm.news.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.searchState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
        vm.refreshNews.observe(viewLifecycleOwner, Observer { subscribeRefreshNews(it) })
    }


    private fun subscribeNewsState(it: Result<List<News>>) {
        when(it) {
            is Result.Loading -> adapter.setState(UiState.Loading)
            is Result.Success -> adapter.setState(UiState.Success)
            is Result.Error -> {
                adapter.setState(UiState.Error)
                Snackbar.make(binding.root, it.errorMessage ?: "", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun subscribeRefreshNews(it: Result<List<News>>) {
        when(it) {
            is Result.Loading -> {
                binding.shimmer.start(requireActivity(), 5, R.layout.placeholder_item_news)
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is Result.Success -> {
                binding.shimmer.stop()
                binding.swipeRefreshLayout.isRefreshing = false
            }
            is Result.Error -> {
                binding.shimmer.stop()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun subscribeNews(data: PagedList<News>) {
        adapter.submitList(data)
    }

    override fun onNewsPressed(news: News) {
        val action =
            HomeFragmentDirections.actionFragmentHomeToNavWebBrowser(
                news.url,
                news.category,
                Origin.NEWS
            )
        findNavController().navigate(action)
    }

    override fun onHeadlinePressed(headline: News) {
        val action =
            HomeFragmentDirections.actionFragmentHomeToNavWebBrowser(
                headline.url,
                headline.category,
                Origin.NEWS
            )
        findNavController().navigate(action)
    }
}
