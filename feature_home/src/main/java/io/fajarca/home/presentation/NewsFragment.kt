package io.fajarca.home.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.fajarca.core.MarvelApp
import io.fajarca.core.vo.UiState
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeBinding
import io.fajarca.home.di.DaggerHomeComponent
import io.fajarca.home.domain.entities.News
import io.fajarca.home.presentation.adapter.NewsRecyclerAdapter
import io.fajarca.navigation.Origin
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class NewsFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), NewsRecyclerAdapter.NewsClickListener {

    @Inject
    lateinit var factory: HomeViewModel.Factory
    private lateinit var adapter: NewsRecyclerAdapter
    override val vm: HomeViewModel by viewModels(factoryProducer = { factory })
    private val args : NewsFragmentArgs by navArgs()

    override fun getLayoutResourceId() = R.layout.fragment_home

    override fun initDaggerComponent() {
        DaggerHomeComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val country = args.country
        val categoryId = args.categoryId
        val category = args.category

        initToolbar(category)
        initRecyclerView()

        vm.setSearchQuery(country, categoryId)

        vm.news.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.initialLoadingState.observe(viewLifecycleOwner, Observer { subscribeInitialLoadingState(it) })
        vm.searchState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
    }

    private fun subscribeInitialLoadingState(it: UiState) {
        when(it) {
            is UiState.Loading -> {
                binding.isLoading = true
            }
            is UiState.Complete -> {
                binding.isLoading = false
            }
        }
    }


    private fun subscribeNewsState(it: UiState) {
        adapter.setState(it)
    }


    private fun initRecyclerView() {
        adapter = NewsRecyclerAdapter(this)
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
    }


    private fun subscribeNews(data: PagedList<News>) {
        adapter.submitList(data)
        binding.isLoading = false
    }

    override fun onNewsPressed(news: News) {
        val action = HomeFragmentDirections.actionFragmentHomeToNavWebBrowser(news.url, news.category, Origin.NEWS)
        findNavController().navigate(action)
    }

    override fun onHeadlinePressed(headline: News) {
        val action = HomeFragmentDirections.actionFragmentHomeToNavWebBrowser(headline.url, headline.category, Origin.NEWS)
        findNavController().navigate(action)
    }

    private fun initToolbar(title : String) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController())
        binding.contentToolbar.toolbar.title = title
    }
}
