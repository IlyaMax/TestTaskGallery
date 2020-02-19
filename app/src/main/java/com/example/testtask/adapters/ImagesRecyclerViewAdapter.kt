package com.example.testtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.models.ImageItem
import com.example.testtask.R
import com.example.testtask.models.Status
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.img_layout.view.*


class ImagesRecyclerViewAdapter(private val onClickListener: ImageItemClickedListener) :
    BaseRecyclerViewAdapter<ImageItem,ImagesRecyclerViewAdapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.img_layout, parent, false)
        )
    }

    inner class ImagesViewHolder(itemView: View) :
        BaseViewHolder<ImageItem>(itemView) {
        override fun onBind(position: Int, item: ImageItem) {
            with(itemView) {
                Picasso.get()
                    .load(item.uri)
                    .fit()
                    .centerCrop()
                    .into(galleryImg)
                container.setOnClickListener {
                    onClickListener.onImageItemClicked(position)
                }
                when (item.status) {
                    Status.LOADING -> {
                        container.isClickable = false
                        imgProgressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        container.isClickable = true
                        imgProgressBar.visibility = View.INVISIBLE
                    }
                    Status.SUCCESS -> {
                        container.isClickable = true
                        imgProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}

interface ImageItemClickedListener {
    fun onImageItemClicked(position: Int)
}


