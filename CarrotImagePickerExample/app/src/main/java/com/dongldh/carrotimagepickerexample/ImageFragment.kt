package com.dongldh.carrotimagepickerexample

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.dongldh.carrotimagepickerexample.`interface`.OnImageClickListener
import com.dongldh.carrotimagepickerexample.adapter.ImageFullAdapter
import com.dongldh.carrotimagepickerexample.data.MediaStoreImage
import com.dongldh.carrotimagepickerexample.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.activity_image.action_back
import kotlinx.android.synthetic.main.activity_image.toolbar_title
import kotlinx.android.synthetic.main.activity_image.view.*
import kotlinx.android.synthetic.main.activity_image.view_pager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_image_full.*

class ImageFragment(val adapter: ImageFullAdapter) : Fragment() {

    private lateinit var imageViewModel: ImageViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_image, container, false).apply {
            val totalImageCount = mainActivity.totalImageSize

            imageViewModel = ViewModelProvider(
                activity as MainActivity,
                ViewModelProvider.AndroidViewModelFactory(mainActivity.application)
            ).get(ImageViewModel::class.java)

            adapter
                .apply {
                    setOnImageClickListener(object: OnImageClickListener {
                        override fun onClickImageLayout(position: Int) {}

                        override fun onClickImageBadge(image: MediaStoreImage) {
                            imageViewModel.addOrRemoveImageFromSelectedList(image)
                        }
                    })
                }
                .also {
                    view_pager.post { view_pager.setCurrentItem(mainActivity.selectedPosition!!, false) }
                    view_pager.adapter = it
                }


            view_pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    toolbar_title.text = "${position + 1} / $totalImageCount"
                }
            })

            action_back.setOnClickListener {
                mainActivity.onBackPressed()
            }
        }
    }
}