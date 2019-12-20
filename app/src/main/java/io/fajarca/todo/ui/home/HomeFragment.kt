package io.fajarca.todo.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.todo.R
import io.fajarca.todo.base.BaseFragment
import io.fajarca.todo.domain.model.response.CharactersResponse
import io.fajarca.todo.databinding.FragmentHomeBinding
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.Result


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

    private fun subscribeCharacters(it: Result<CharactersResponse>) {
        when(it.status) {
            Result.Status.LOADING -> {

            }
            Result.Status.SUCCESS -> {
                val characters = mutableListOf<Character>()

                val data = it.data?.data?.results ?: emptyList()
                for (i in data) {
                    characters.add(
                        Character(
                            i.id,
                            i.name,
                            "${i.thumbnail.path}/portrait_uncanny.${i.thumbnail.extension}"
                        )
                    )
                }

                refreshData(characters)
            }
            Result.Status.ERROR -> {

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

}
