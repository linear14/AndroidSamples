package com.dongldh.carrotimagepickerexample.viewModel

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageViewModel(private val app: Application): AndroidViewModel(app) {

    val images = MutableLiveData<List<MediaStoreImage>>()

    suspend fun queryImages(){
        val imageList = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN
            )
            val selection = null
            val selectionArgs = null
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

            app.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val dateTaken = Date(cursor.getLong(dateTakenColumn))
                    val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )

                    val image = MediaStoreImage(id, dateTaken, uri)
                    imageList += image

                    Log.d("IMAGE_INFO", image.toString())
                }
            }
        }

        GlobalScope.launch { images.postValue(imageList) }
    }

}