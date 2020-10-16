package com.dongldh.carrotimagepickerexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import com.dongldh.carrotimagepickerexample.paging.ImageDataSourceFactory

class ImageViewModel(private val app: Application) : AndroidViewModel(app) {

    val images: LiveData<PagedList<MediaStoreImage>> by lazy {
        getImageList()
    }

    val selectedImages = mutableListOf<MediaStoreImage>()

    fun getImageList(): LiveData<PagedList<MediaStoreImage>> {
        val dataSourceFactory = ImageDataSourceFactory(app.contentResolver)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(21)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    fun addOrRemoveImageFromSelectedList(image: MediaStoreImage) {
        if(image in selectedImages) {
            selectedImages.remove(image)
        } else {
            selectedImages.add(image)
        }
    }
}
