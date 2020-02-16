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
import io.fajarca.core.BuzzNewsApp
import io.fajarca.core.vo.Result
import io.fajarca.navigation.Origin
import io.fajarca.news_channel.R
import io.fajarca.news_channel.databinding.FragmentNewsChannelBinding
import io.fajarca.news_channel.di.DaggerNewsChannelComponent
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.presentation.adapter.NewsChannelRecyclerAdapter
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class NewsChannelFragment : BaseFragment<FragmentNewsChannelBinding, NewsChannelViewModel>(),
    NewsChannelRecyclerAdapter.NewsChannelClickListener {

    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.fragmentNewsChannel).build() }

    @Inject
    lateinit var factory: NewsChannelViewModel.Factory
    private lateinit var adapter: NewsChannelRecyclerAdapter
    override val vm: NewsChannelViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_news_channel

    override fun initDaggerComponent() {
        DaggerNewsChannelComponent
            .builder()
            .coreComponent(BuzzNewsApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        vm.getNewsChannel()
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
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    override fun onNewsChannelPressed(channel: NewsChannel) {
        val action = NewsChannelFragmentDirections.actionFragmentNewsChannelToNavWebBrowser(channel.url, channel.name, Origin.CHANNEL)
        findNavController().navigate(action)
    }


}
