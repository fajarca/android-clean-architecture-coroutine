package io.fajarca.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.fajarca.core.MarvelApp
import io.fajarca.core.vo.UiState
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeBinding
import io.fajarca.home.di.DaggerCharacterListComponent
import io.fajarca.home.domain.entities.News
import io.fajarca.home.presentation.adapter.NewsRecyclerAdapter
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


        vm.newsSource.observe(viewLifecycleOwner, Observer { subscribeNews(it) })
        vm.newsSourceState.observe(viewLifecycleOwner, Observer { subscribeNewsState(it) })
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
        Timber.v("Pagedlist size : ${data.size}")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }


    override fun onNewsPressed(news: News) {

    }

}
