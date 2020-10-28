package com.dongldh.carrotimagepickerexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.dongldh.carrotimagepickerexample.R
import com.dongldh.carrotimagepickerexample.`interface`.OnImageClickListener
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import kotlinx.android.synthetic.main.item_image_full.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageFullAdapter : ListAdapter<MediaStoreImage, ImageFullAdapter.ImageFullViewHolder>(ImageDiffCallback()) {

    private var imageClickListener: OnImageClickListener? = null
    var selectedImages: List<MediaStoreImage>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFullViewHolder {
        return ImageFullViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_full, parent, false))
    }

    override fun onBindViewHolder(holder: ImageFullViewHolder, position: Int) {
        val item = getItem(position)!!.also { holder.bind(it) }

        GlobalScope.launch(Dispatchers.IO) {
            val builder = Glide.with(holder.itemView)
                .load(item.uri)
                .override(Target.SIZE_ORIGINAL)

            GlobalScope.launch(Dispatchers.Main) {
                builder.into(holder.image)
            }
        }
    }

    inner class ImageFullViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image
        val layoutBadge = view.layout_badge
        val numbering = view.numbering

        fun bind(item: MediaStoreImage) {
            layoutBadge.setOnClickListener { imageClickListener?.onClickImageBadge(item) }
            bindUI(item)
        }

        private fun bindUI(mediaStoreImage: MediaStoreImage) {
            selectedImages?.let { list ->
                if (mediaStoreImage in list) {
                    numbering.text = (list.indexOf(mediaStoreImage) + 1).toString()
                    layoutBadge.setBackgroundResource(R.drawable.deco_orange_background)
                } else {
                    numbering.text = ""
                    layoutBadge.setBackgroundResource(R.drawable.deco_white_circle)
                }
            }
        }
    }

    fun setOnImageClickListener(li: OnImageClickListener) {
        imageClickListener = li
    }
}