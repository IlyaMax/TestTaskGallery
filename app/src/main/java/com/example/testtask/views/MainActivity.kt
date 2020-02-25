package com.example.testtask.views

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.App
import com.example.testtask.R


class MainActivity : AppCompatActivity() {
    val GALLERY_KEY = "gallery is showing"
    //ключ по которому определяется какой фрагмент был запущен перед сменой конфигурации
    private var gallery = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (App.isLarge) {
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
            if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GalleryFragment())
                .commit()
        }
    }
}



