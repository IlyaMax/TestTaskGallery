package com.example.testtask.views

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.R


class MainActivity : AppCompatActivity() {
    val GALLERY_KEY = "gallery is showing"
    //ключ по которому определяется какой фрагмент был запущен перед сменой конфигурации
    private var gallery = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isLarge()) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    GalleryFragment()
                )
                .replace(
                    R.id.additionalContainer,
                    LinksFragment()
                )
                .commit()
        } else {
            gallery = savedInstanceState?.getBoolean(GALLERY_KEY) ?: true
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, if (gallery) GalleryFragment() else LinksFragment())
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!isLarge()) outState.putBoolean(
            GALLERY_KEY,
            supportFragmentManager.fragments[0] is GalleryFragment
        )
    }

    private fun isLarge() = ((resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE)
}



