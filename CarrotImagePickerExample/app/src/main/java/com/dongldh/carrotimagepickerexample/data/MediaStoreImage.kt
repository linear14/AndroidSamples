package com.dongldh.carrotimagepickerexample.data

import android.net.Uri
import java.util.*

data class MediaStoreImage(
    val id: Long,
    val date: Date,
    val uri: Uri
) {
}