package com.bangkit2024.huetiful.ui.fragments.favorite

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
import com.bangkit2024.huetiful.databinding.ItemFavoriteBinding
import com.bangkit2024.huetiful.ui.activity.result.ResultActivity

class FavoriteAdapter : ListAdapter<GetFavoriteDataResponseItem, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetFavoriteDataResponseItem) {
            Log.d("FavoriteAdapter", "palate : ${data.predictedPalette}")

            val predictedPalette: List<String> = data.predictedPalette?.let { palette ->
                palette.split(",").map { color ->
                    color.trim().trim('[', ']', '"')
                }
            } ?: emptyList()

            binding.vColor1.setBackgroundColor(Color.parseColor(predictedPalette[0]))
            binding.vColor2.setBackgroundColor(Color.parseColor(predictedPalette[1]))
            binding.vColor3.setBackgroundColor(Color.parseColor(predictedPalette[2]))
            binding.vColor4.setBackgroundColor(Color.parseColor(predictedPalette[3]))
            binding.vColor5.setBackgroundColor(Color.parseColor(predictedPalette[4]))

            binding.ivUpper.setBackgroundColor(Color.parseColor(data.extractedSkinTone))

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ResultActivity::class.java)
                val bundle = Bundle()
                val palateArrayList = predictedPalette.toCollection(ArrayList())
                bundle.putStringArrayList("colorList", palateArrayList)
                bundle.putString("extractedSkinTone", data.extractedSkinTone)
                intent.putExtras(bundle)

                val optionCompact: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivUpper, "itemImage")
                    )
                itemView.context.startActivity(intent, optionCompact.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetFavoriteDataResponseItem>() {
            override fun areItemsTheSame(
                oldItem: GetFavoriteDataResponseItem,
                newItem: GetFavoriteDataResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GetFavoriteDataResponseItem,
                newItem: GetFavoriteDataResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}