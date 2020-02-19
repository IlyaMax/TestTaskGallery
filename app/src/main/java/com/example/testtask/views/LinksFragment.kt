package com.example.testtask.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.*
import com.example.testtask.adapters.LinkClickedListener
import com.example.testtask.adapters.LinksRecyclerViewAdapter
import com.example.testtask.models.LinkEntity
import com.example.testtask.models.Status
import com.example.testtask.viewmodels.LinksViewModel
import kotlinx.android.synthetic.main.urls_fragment.*

class LinksFragment : Fragment(),
    LinkClickedListener {
    private lateinit var linksViewModel: LinksViewModel
    private lateinit var adapter: LinksRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.urls_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linksViewModel = activity?.run {
            ViewModelProvider(this).get(LinksViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        mToolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        mToolbar?.setNavigationOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
                .replace(
                    R.id.fragmentContainer,
                    GalleryFragment()
                )
                .commit()
        }
        mToolbar?.title = resources.getString(R.string.links_fragment_title)
        adapter = LinksRecyclerViewAdapter(this)
        rvLinks.adapter = adapter
        observeLinksFromDB()
    }

    private fun observeLinksFromDB() {
        linksViewModel.getAllLinks().observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }

    override fun onLinkClicked(position: Int) {
        val item = adapter.getItemByIndex(position)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
    }
}