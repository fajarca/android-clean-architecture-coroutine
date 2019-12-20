package io.fajarca.todo.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.todo.R
import io.fajarca.todo.base.BaseFragment
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.databinding.FragmentHomeBinding
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import timber.log.Timber


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

    private fun subscribeCharacters(it: Result<CharacterDto>) {
        when(it) {
            is Result.Loading-> {
                Timber.v("Loading")
            }
            is Result.Success-> {
                val characters = mutableListOf<Character>()

                val data = it.data.data.results
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
            is Result.Error -> {
                Timber.v("Error ${it.cause}")
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
