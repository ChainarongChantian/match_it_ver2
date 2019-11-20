package com.example.android.navigation.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.database.UserData
import com.example.android.navigation.databinding.ScoreListBinding

class RankingAdapter() : ListAdapter<UserData,RankingAdapter.ScoreViewHolder>(DiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding =
                ScoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = getItem(position)
        holder.bind(score)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.userId == newItem.userId
        }
    }

    class ScoreViewHolder(private var binding:
                          ScoreListBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(score: UserData) {
            binding.userScore = score
            binding.executePendingBindings()
        }
    }
}