package io.fajarca.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.databinding.ItemCharacterBinding

class CharactersRecyclerAdapter(private val listener: CharacterClickListener) : androidx.recyclerview.widget.ListAdapter<CharacterEntity, CharactersRecyclerAdapter.CharacterViewHolder>(
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

        fun bind(character: CharacterEntity, listener: CharacterClickListener) {
            binding.character = character
            binding.root.setOnClickListener { listener.onCharacterPressed() }
            binding.executePendingBindings()
        }
    }

    interface CharacterClickListener {
        fun onCharacterPressed()
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}