package io.fajarca.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.home.databinding.ItemCharacterBinding
import io.fajarca.home.domain.MarvelCharacter

class CharactersRecyclerAdapter(private val listener: CharacterClickListener) : ListAdapter<MarvelCharacter, CharactersRecyclerAdapter.CharacterViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent,false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    inner class CharacterViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: MarvelCharacter, listener: CharacterClickListener) {
            binding.character = character
            binding.root.setOnClickListener { listener.onCharacterPressed(character) }
            binding.executePendingBindings()
        }
    }

    interface CharacterClickListener {
        fun onCharacterPressed(character: MarvelCharacter)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MarvelCharacter>() {
            override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
                return oldItem == newItem
            }

        }
    }
}