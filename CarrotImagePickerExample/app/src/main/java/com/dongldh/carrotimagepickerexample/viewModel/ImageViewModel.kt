package com.dongldh.carrotimagepickerexample.viewModel

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import com.dongldh.carrotimagepickerexample.paging.ImageDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageViewModel(private val app: Application) : AndroidViewModel(app) {

    val images: LiveData<PagedList<MediaStoreImage>> by lazy {
        getImageList()
    }

    fun getImageList(): LiveData<PagedList<MediaStoreImage>> {
        val dataSourceFactory = ImageDataSourceFactory(app.contentResolver)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(21)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }
}
