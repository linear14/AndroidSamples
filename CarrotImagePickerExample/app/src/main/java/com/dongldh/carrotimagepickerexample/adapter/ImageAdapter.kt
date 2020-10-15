package com.dongldh.carrotimagepickerexample.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dongldh.carrotimagepickerexample.R
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter: ListAdapter<MediaStoreImage, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {
    private var imageClickListener: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val mediaStoreImage = getItem(position)

        Glide.with(holder.imageView)
            .load(mediaStoreImage.uri)
            .centerCrop()
            .into(holder.imageView)

        holder.imageLayout.setOnClickListener {
            imageClickListener?.onClickImageLayout(mediaStoreImage.uri)
        }

        holder.badgeLayout.setOnClickListener {
            imageClickListener?.onClickImageBadge()
        }
    }

    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView = view.image
        val imageLayout = view.layout_image
        val badgeLayout = view.layout_badge
    }

    fun setOnImageClickListener(li: OnImageClickListener) {
        imageClickListener = li
    }

    interface OnImageClickListener {
        fun onClickImageLayout(uri: Uri)
        fun onClickImageBadge()
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