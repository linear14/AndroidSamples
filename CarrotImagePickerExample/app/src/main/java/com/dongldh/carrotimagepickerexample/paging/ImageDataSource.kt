package com.dongldh.carrotimagepickerexample.paging

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageDataSourceFactory(private val contentResolver: ContentResolver) : DataSource.Factory<Int, MediaStoreImage>() {
    override fun create(): DataSource<Int, MediaStoreImage> {
        return ImageDataSource(contentResolver)
    }
}

class ImageDataSource(private val contentResolver: ContentResolver): PositionalDataSource<MediaStoreImage>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MediaStoreImage>) {
        GlobalScope.launch {
            callback.onResult(queryImages(params.loadSize, params.startPosition))
        }
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MediaStoreImage>
    ) {
        GlobalScope.launch {
            callback.onResult(queryImages(params.requestedLoadSize, params.requestedStartPosition), 0)
        }
    }

    private suspend fun queryImages(limit: Int, offset: Int): MutableList<MediaStoreImage>{
        val imageList = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN
            )
            val selection = null
            val selectionArgs = null
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC LIMIT $limit OFFSET $offset"

            contentResolver.query(
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

                    val image = MediaStoreImage(id, dateTaken, uri.toString())
                    imageList += image

                    Log.d("IMAGE_INFO", image.toString())
                }
            }
        }
        return imageList
    }

}