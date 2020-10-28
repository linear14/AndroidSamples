package com.dongldh.carrotimagepickerexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.dongldh.carrotimagepickerexample.`interface`.OnImageClickListener
import com.dongldh.carrotimagepickerexample.adapter.ImageAdapter
import com.dongldh.carrotimagepickerexample.adapter.ImageFullAdapter
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import com.dongldh.carrotimagepickerexample.permission.Permission.haveStoragePermission
import com.dongldh.carrotimagepickerexample.permission.Permission.requestPermission
import com.dongldh.carrotimagepickerexample.util.App
import com.dongldh.carrotimagepickerexample.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_image_full.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_STORAGE_REQUEST = 0x1001
    }

    private lateinit var imageViewModel: ImageViewModel
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageFullAdapter: ImageFullAdapter

    var totalImageSize: Int? = null
    var selectedPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageFullAdapter = ImageFullAdapter()

        imageViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(ImageViewModel::class.java)

        imageAdapter = ImageAdapter()
            .also { recycler.adapter = it }
            .apply {
                setOnImageClickListener(object: OnImageClickListener {
                    override fun onClickImageLayout(position: Int) {
                        selectedPosition = position
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, ImageFragment(imageFullAdapter)).commitAllowingStateLoss()
                        frame_layout.visibility = View.VISIBLE
                    }

                    override fun onClickImageBadge(image: MediaStoreImage) {
                        imageViewModel.addOrRemoveImageFromSelectedList(image)
                    }

                })
            }

        openMediaStore()
    }

    private fun openMediaStore() {
        if(haveStoragePermission()) {
            observeImages()
            observeSelectedImages()
        } else {
            requestPermission(this)
        }
    }

    private fun observeImages() {
        imageViewModel.getImageList()
        imageViewModel.images.observe(this) { result ->
            imageAdapter.submitList(result)
            imageFullAdapter.submitList(result)
            totalImageSize = result.size
        }
    }

    private fun observeSelectedImages() {
        imageViewModel.selectedImagesLiveData.observe(this) { result ->
            imageAdapter.selectedImages = result
            imageFullAdapter.selectedImages = result
            imageAdapter.notifyDataSetChanged()
            imageFullAdapter.notifyDataSetChanged()
        }
    }

    private fun goToSettings() {
        Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { startActivity(it) }
    }

    override fun onBackPressed() {
        if(frame_layout.visibility == View.VISIBLE) {
            frame_layout.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    observeImages()
                    observeSelectedImages()
                } else {
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    if(!showRationale) {
                        goToSettings()
                    }
                }
                return
            }
        }
    }
}