package io.fajarca.home.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.core.MarvelApp
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeBinding
import io.fajarca.home.di.DaggerFeatureComponent
import io.fajarca.home.domain.MarvelCharacter
import io.fajarca.presentation.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    CharactersRecyclerAdapter.CharacterClickListener {

    private lateinit var adapter : CharactersRecyclerAdapter

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun initDaggerComponent() {
        DaggerFeatureComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        vm.getAllCharacters()

        vm.characters.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: HomeViewModel.CharacterState<List<MarvelCharacter>>) {
        when(it) {
            is HomeViewModel.CharacterState.Loading -> {
                showLoading(true)
            }
            is HomeViewModel.CharacterState.Empty -> {
                showLoading(false)
            }
            is HomeViewModel.CharacterState.Success -> {
                showLoading(false)
                refreshData(it.data)
            }
        }
    }


    private fun initRecyclerView() {
        adapter = CharactersRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(activity,2 )
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshData(characters: List<MarvelCharacter>) {
        adapter.submitList(characters)
    }


    override fun onCharacterPressed() {

    }

    private fun showLoading(isLoading : Boolean) {
        binding.isLoading = isLoading
    }

}
