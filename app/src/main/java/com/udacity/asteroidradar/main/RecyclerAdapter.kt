package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.RecyclerItemBinding

class RecyclerAdapter(val onClickListener: OnClickListener) : androidx.recyclerview.widget.ListAdapter<Asteroid, RecyclerAdapter.AsteroidViewHolder>(DiffCallback) {

    class AsteroidViewHolder(private var binding: RecyclerItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asteroid) {
            binding.aster = item
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.AsteroidViewHolder {
        return AsteroidViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.AsteroidViewHolder, position: Int) {
        val Property = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(Property) }
        holder.bind(Property)
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
class OnClickListener(val clickListener: (item: Asteroid) -> Unit) {
    fun onClick(item: Asteroid) = clickListener(item)
}


