package com.rino.translator.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rino.translator.core.model.Word
import com.rino.translator.databinding.WordItemBinding
import com.rino.translator.ui.base.ImageLoader

class WordsAdapter(
    private val imageLoader: ImageLoader<ImageView>,
    private val itemClickListener: (Word) -> Unit
) : ListAdapter<Word, WordsAdapter.UserViewHolder>(WordItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class UserViewHolder(
        private val binding: WordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            with(binding) {
                root.setOnClickListener { itemClickListener(word) }
                header.text = word.text

                word.meanings?.firstOrNull()?.let { meaning ->
                    description.text = meaning.translation?.text
                    meaning.previewUrl?.let { imageLoader.loadInto("https:$it", binding.previewImage) }
                }
            }
        }
    }

}

class WordItemCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}