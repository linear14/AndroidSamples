package com.dongldh.carrotimagepickerexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrotimagepickerexample.R
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import com.dongldh.carrotimagepickerexample.databinding.ItemImageBinding

class ImageAdapter: PagedListAdapter<MediaStoreImage, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {
    private var imageClickListener: OnImageClickListener? = null
    val selectedImages = mutableListOf<MediaStoreImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val mediaStoreImage = getItem(position)!!
        holder.bind(mediaStoreImage)
    }

    inner class ImageViewHolder(val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaStoreImage) {
            binding.apply {
                mediaStoreImage = item
                executePendingBindings()

                layoutImage.setOnClickListener { imageClickListener?.onClickImageLayout(item.uri) }
                layoutBadge.setOnClickListener { changeImageSelectedState(item) }
            }
            bindUI(item)
        }


        private fun changeImageSelectedState(mediaStoreImage: MediaStoreImage) {
            if(mediaStoreImage in selectedImages) {
                selectedImages.remove(mediaStoreImage)
            } else {
                selectedImages.add(mediaStoreImage)
            }

            notifyDataSetChanged()
            imageClickListener?.onClickImageBadge(mediaStoreImage)
        }

        private fun bindUI(mediaStoreImage: MediaStoreImage) {
            binding.apply {
                if(mediaStoreImage in selectedImages) {
                    numbering.text = (selectedImages.indexOf(mediaStoreImage) + 1).toString()
                    imageFilter.visibility = View.VISIBLE
                    layoutBadge.setBackgroundResource(R.drawable.deco_orange_background)
                } else {
                    numbering.text = ""
                    imageFilter.visibility = View.GONE
                    layoutBadge.setBackgroundResource(R.drawable.deco_white_circle)
                }
            }
        }
    }

    fun setOnImageClickListener(li: OnImageClickListener) {
        imageClickListener = li
    }

    interface OnImageClickListener {
        fun onClickImageLayout(uri: String)
        fun onClickImageBadge(image: MediaStoreImage)
    }

}

class ImageDiffCallback: DiffUtil.ItemCallback<MediaStoreImage>() {
    override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem == newItem
    }

}