package com.bangkit2024.huetiful.ui.fragments.favorite

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.huetiful.data.local.model.PalateModel
import com.bangkit2024.huetiful.databinding.ItemFavoriteBinding
import com.bangkit2024.huetiful.ui.activity.result.ResultActivity

class FavoriteAdapter : ListAdapter<PalateModel, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        private val circleImageView1 = binding.vColor1
        private val circleImageView2 = binding.vColor2
        private val circleImageView3 = binding.vColor3
        private val circleImageView4 = binding.vColor4
        private val circleImageView5 = binding.vColor5

        private val iFavorite = binding.iFavorite

        fun bind(data: PalateModel) {
            circleImageView1.setBackgroundColor(Color.parseColor(data.color1))
            circleImageView2.setBackgroundColor(Color.parseColor(data.color2))
            circleImageView3.setBackgroundColor(Color.parseColor(data.color3))
            circleImageView4.setBackgroundColor(Color.parseColor(data.color4))
            circleImageView5.setBackgroundColor(Color.parseColor(data.color5))

            iFavorite.setOnClickListener {
                makeToast("Icon favorite clicked")
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ResultActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }

        private fun makeToast(message: String) {
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PalateModel>() {
            override fun areItemsTheSame(
                oldItem: PalateModel,
                newItem: PalateModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PalateModel,
                newItem: PalateModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}