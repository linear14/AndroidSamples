package com.dongldh.carrotimagepickerexample.`interface`

import com.dongldh.carrotimagepickerexample.data.MediaStoreImage

interface OnImageClickListener {
    fun onClickImageLayout(position: Int)
    fun onClickImageBadge(image: MediaStoreImage)
}