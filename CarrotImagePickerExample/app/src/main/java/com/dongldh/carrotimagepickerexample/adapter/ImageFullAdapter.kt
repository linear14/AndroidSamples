package com.dongldh.carrotimagepickerexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.dongldh.carrotimagepickerexample.R
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import kotlinx.android.synthetic.main.item_image_full.view.*
import java.util.*

class ImageFullAdapter: ListAdapter<MediaStoreImage, ImageFullAdapter.ImageFullViewHolder>(ImageDiffCallback()) {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFullViewHolder {
         return ImageFullViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_full, parent, false))
     }

     override fun onBindViewHolder(holder: ImageFullViewHolder, position: Int) {
         val item = getItem(position)!!

         Glide.with(holder.itemView)
             .load(item.uri)
             .into(holder.image)
     }

     inner class ImageFullViewHolder(view: View): RecyclerView.ViewHolder(view) {
         val image = view.image
         val layoutBadge = view.layout_badge
         val numbering = view.numbering
     }
}