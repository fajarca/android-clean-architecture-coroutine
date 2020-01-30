package io.fajarca.home.presentation.list

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
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentCharactersBinding
import io.fajarca.home.di.DaggerCharacterListComponent
import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.core.MarvelApp
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentCharactersBinding, HomeViewModel>(),
    CharactersRecyclerAdapter.CharacterClickListener {

    @Inject
    lateinit var factory: HomeViewModel.Factory
    private lateinit var adapter: CharactersRecyclerAdapter
    override val vm: HomeViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_characters


    override fun initDaggerComponent() {
        DaggerCharacterListComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        vm.getTopHeadlines()

        vm.topHeadlines.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: HomeViewModel.TopHeadlinesState<List<TopHeadline>>) {
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
            }
        }
    }


    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun initRecyclerView() {
        adapter = CharactersRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshData(characters: List<TopHeadline>) {
        adapter.submitList(characters)
    }


    override fun onCharacterPressed(character: TopHeadline) {

    }

    private fun showLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

}
