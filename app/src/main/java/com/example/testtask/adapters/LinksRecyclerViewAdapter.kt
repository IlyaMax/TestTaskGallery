package com.example.testtask.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.models.LinkEntity
import com.example.testtask.R
import kotlinx.android.synthetic.main.link_layout.view.*

class LinksRecyclerViewAdapter(private val onClickListener: LinkClickedListener) :
    BaseRecyclerViewAdapter<LinkEntity, LinksRecyclerViewAdapter.LinksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        return LinksViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.link_layout, parent, false)
        )
    }

    fun addItem(linkEntity: LinkEntity){
        items.add(linkEntity)
        notifyDataSetChanged()
    }

    inner class LinksViewHolder(itemView: View) :
        BaseViewHolder<LinkEntity>(itemView) {
        override fun onBind(position: Int, item: LinkEntity) {
            with(itemView) {
                val mLinkText = item.link.split("com/")[1]
                tvLink.text = Html.fromHtml("<p><u>.../$mLinkText</u></p>")
                tvLink.setOnClickListener {
                    onClickListener.onLinkClicked(position)
                }
            }
        }
    }
}

interface LinkClickedListener {
    fun onLinkClicked(position: Int)
}