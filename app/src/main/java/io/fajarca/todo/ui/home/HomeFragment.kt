package io.fajarca.todo.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.todo.R
import io.fajarca.todo.databinding.FragmentHomeBinding
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.local.Character
import io.fajarca.todo.ui.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    CharactersRecyclerAdapter.CharacterClickListener {

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java
    private lateinit var adapter : CharactersRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        vm.getAllCharacters()

        vm.characters.observe(this, Observer { subscribeCharacters(it) })
    }

    private fun subscribeCharacters(it: HomeViewModel.Result<List<Character>>) {
        when(it) {
            is HomeViewModel.Result.Loading-> {
                showLoading(true)
            }
            is HomeViewModel. Result.Success-> {
                showLoading(false)
                refreshData(it.data)
            }
            is HomeViewModel.Result.Error -> {
                showLoading(false)
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

    private fun refreshData(characters: List<Character>) {
        adapter.submitList(characters)
    }


    override fun onCharacterPressed() {

    }

    private fun showLoading(isLoading : Boolean) {
        binding.isLoading = isLoading
    }

}
