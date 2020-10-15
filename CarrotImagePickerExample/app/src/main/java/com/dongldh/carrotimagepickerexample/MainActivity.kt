package com.dongldh.carrotimagepickerexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.dongldh.carrotimagepickerexample.adapter.ImageAdapter
import com.dongldh.carrotimagepickerexample.permission.Permission.haveStoragePermission
import com.dongldh.carrotimagepickerexample.permission.Permission.requestPermission
import com.dongldh.carrotimagepickerexample.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045
    }

    private lateinit var imageViewModel: ImageViewModel
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(ImageViewModel::class.java)

        imageAdapter = ImageAdapter()
            .also { recycler.adapter = it }
            .apply {
                setOnImageClickListener(object: ImageAdapter.OnImageClickListener {
                    override fun onClickImageLayout(uri: Uri) {
                        Intent(this@MainActivity, ImageActivity::class.java)
                            .apply { putExtra("uri", uri.toString()) }
                            .also { startActivity(it) }
                    }

                    override fun onClickImageBadge() {
                        Toast.makeText(this@MainActivity, "Not yet implemented", Toast.LENGTH_SHORT).show()
                    }

                })
            }

        openMediaStore()
    }

    private fun openMediaStore() {
        if(haveStoragePermission()) {
            observeImages()
        } else {
            requestPermission(this)
        }
    }

    private fun observeImages() {
        imageViewModel.images.observe(this) { result ->
            imageAdapter.submitList(result)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    observeImages()
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