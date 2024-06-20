package com.bangkit2024.huetiful.ui.activity.result

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.local.model.DetailPalateModel
import com.bangkit2024.huetiful.databinding.ItemDetailBinding

class ResultAdapter : ListAdapter<DetailPalateModel, ResultAdapter.FullDetailViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullDetailViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FullDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FullDetailViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DetailPalateModel) {

            binding.vItemDetail.setBackgroundColor(Color.parseColor(data.color))
            binding.tvItemInfo.text = data.color

            if (data.name != null) {
                Log.d("ColorList", data.name!!)
                binding.tvItemName.text = data.name
            }

//            try {
//                binding.vItemDetail.setBackgroundColor(Color.parseColor(data.color))
//                binding.tvItemInfo.text = data.color
//            } catch (e: IllegalArgumentException) {
//                binding.vItemDetail.setBackgroundColor(Color.WHITE)
//                binding.tvItemInfo.text = itemView.context.getString(R.string.white_hexsa)
//            }

            binding.iCopyInfo.setOnClickListener {
                val clipboardManager = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Copied Text", data.color)
                clipboardManager.setPrimaryClip(clipData)
                if (clipData != null) {
                    makeToast("Item copied!")
                } else {
                    makeToast("Failed to copy item")
                }
                animateIcon()
            }
        }

        private fun animateIcon() {
            val darkMode = AppCompatDelegate.getDefaultNightMode()
            when (darkMode) {
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    binding.iCopyInfo.setImageResource(R.drawable.copy_anim_linear2)
                    val copyButtonAnim = binding.iCopyInfo.drawable as AnimatedVectorDrawable
                    copyButtonAnim.start()
                }
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    binding.iCopyInfo.setImageResource(R.drawable.copy_anim_linear)
                    val copyButtonAnim = binding.iCopyInfo.drawable as AnimatedVectorDrawable
                    copyButtonAnim.start()
                }
            }
        }

        private fun makeToast(message: String) {
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailPalateModel>() {
            override fun areItemsTheSame(
                oldItem: DetailPalateModel,
                newItem: DetailPalateModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailPalateModel,
                newItem: DetailPalateModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}