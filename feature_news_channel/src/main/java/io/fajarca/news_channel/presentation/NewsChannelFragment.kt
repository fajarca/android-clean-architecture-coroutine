package io.fajarca.news_channel.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.core.MarvelApp
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.R
import io.fajarca.news_channel.databinding.FragmentNewsChannelBinding
import io.fajarca.news_channel.di.DaggerNewsChannelComponent
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.presentation.adapter.NewsChannelRecyclerAdapter
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class NewsChannelFragment : BaseFragment<FragmentNewsChannelBinding, NewsChannelViewModel>(),
    NewsChannelRecyclerAdapter.NewsChannelClickListener {

    @Inject
    lateinit var factory: NewsChannelViewModel.Factory
    private lateinit var adapter: NewsChannelRecyclerAdapter
    override val vm: NewsChannelViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_news_channel

    override fun initDaggerComponent() {
        DaggerNewsChannelComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        vm.newsChannel.observe(viewLifecycleOwner, Observer { subscribeNewsChannel(it) })
    }


    private fun initRecyclerView() {
        adapter = NewsChannelRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapter
    }


    private fun subscribeNewsChannel(it: Result<List<NewsChannel>>) {
        when(it) {
            is Result.Loading -> {
                showLoading(true)
            }
            is Result.Success -> {
                showLoading(false)
                adapter.submitList(it.data)
            }
            is Result.Error -> {
                showLoading(false)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    override fun onNewsChannelPressed(news: NewsChannel) {

    }


}
