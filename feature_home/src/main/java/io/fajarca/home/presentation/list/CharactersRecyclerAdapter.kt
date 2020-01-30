package io.fajarca.home.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.home.databinding.ItemCharacterBinding
import io.fajarca.home.domain.entities.TopHeadline

class CharactersRecyclerAdapter(private val listener: CharacterClickListener) : ListAdapter<TopHeadline, CharactersRecyclerAdapter.CharacterViewHolder>(
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

        fun bind(character: TopHeadline, listener: CharacterClickListener) {
            binding.character = character
            binding.root.setOnClickListener { listener.onCharacterPressed(character) }
            binding.executePendingBindings()
        }
    }

    interface CharacterClickListener {
        fun onCharacterPressed(character: TopHeadline)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TopHeadline>() {
            override fun areItemsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem == newItem
            }

        }
    }
}