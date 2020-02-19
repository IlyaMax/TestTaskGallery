package com.example.testtask.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.*
import com.example.testtask.adapters.ImageItemClickedListener
import com.example.testtask.adapters.ImagesRecyclerViewAdapter
import com.example.testtask.models.Status
import com.example.testtask.viewmodels.GalleryViewModel
import com.example.testtask.viewmodels.LinksViewModel
import kotlinx.android.synthetic.main.gallery_fragment.*

class GalleryFragment : Fragment(),
    ImageItemClickedListener {
    val PERMISSION_REQUEST_CODE = 12345
    private lateinit var adapter: ImagesRecyclerViewAdapter
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var linksViewModel: LinksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryViewModel = activity?.run {
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        linksViewModel = activity?.run {
            ViewModelProvider(this).get(LinksViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        mToolbar?.navigationIcon = null
        mToolbar?.title = resources.getString(R.string.app_name)
        mToolbar?.inflateMenu(R.menu.toolbar_menu)
        mToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_show_links -> {
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(
                            R.id.fragmentContainer,
                            LinksFragment()
                        )
                        .commit()
                    true
                }
                else -> false
            }
        }
        adapter = ImagesRecyclerViewAdapter(this)
        rvImgs.adapter = adapter
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            observeImagesFromGallery()
            observeLoadingChanges()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                observeImagesFromGallery()
                observeLoadingChanges()
            } else {
                Toast.makeText(context, "Ну пожалуйста, дайте разрешение", Toast.LENGTH_LONG).show()
                activity!!.finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun observeImagesFromGallery() {
        galleryViewModel.galleryImagesData
            .observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
            })
    }

    private fun observeLoadingChanges() {
        linksViewModel.linkStates.observe(viewLifecycleOwner, Observer {
            it.forEach { position, linkState ->
                val imageItem = adapter.getItemByIndex(position)
                if (imageItem.status != linkState.status) {
                    imageItem.status = linkState.status
                    adapter.changeItem(position, imageItem)
                    if (linkState.status == Status.SUCCESS) Toast.makeText(
                        context,
                        "Успешно отправлено",
                        Toast.LENGTH_SHORT
                    ).show()
                    else if (linkState.status == Status.ERROR) Toast.makeText(
                        context,
                        "Ошибка отправки",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onImageItemClicked(position: Int) {
        if (isNetworkConnected()) {
            val imageItem = adapter.getItemByIndex(position)
            linksViewModel.uploadImage(position, imageItem)
        } else Toast.makeText(context, "Нет соединения", Toast.LENGTH_SHORT).show()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}