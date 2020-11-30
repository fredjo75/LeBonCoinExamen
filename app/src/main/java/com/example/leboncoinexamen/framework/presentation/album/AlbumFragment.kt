package com.example.leboncoinexamen.framework.presentation.album

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.leboncoinexamen.R
import com.example.leboncoinexamen.databinding.FragmentAlbumListBinding
import com.example.leboncoinexamen.di.ApplicationComponent
import com.example.leboncoinexamen.framework.presentation.BaseFragment
import com.example.leboncoinexamen.framework.presentation.MyApplication
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    private var columnCount = 1

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var binding: FragmentAlbumListBinding
    private lateinit var viewModel: AlbumViewModel

    companion object {
        private var PORTRAIT_COLUMN_COUNT = 5
        private var LANDSCAPE_COLUMN_COUNT = 10
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumListBinding.inflate(inflater)
        viewModel = viewModelProvider.create(AlbumViewModel::class.java)

        binding.viewModel = viewModel

        val albumAdapter = AlbumAdapter(requireContext(), AlbumAdapter.AlbumClickListener {
            binding.root.findNavController().navigate(R.id.action_albumFragment_to_photoFragment2)
        })

        binding.list.adapter = albumAdapter

        binding.lifecycleOwner = this

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                albumAdapter.addHeaderAndSubmitList(it)
            }
        })

        columnCount = when (requireContext().resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMN_COUNT
            Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMN_COUNT
            else -> 1
        }

        val manager = GridLayoutManager(context, columnCount)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (albumAdapter.getItemViewType(position)) {
                    AlbumAdapter.ITEM_VIEW_TYPE_HEADER -> columnCount
                    AlbumAdapter.ITEM_VIEW_TYPE_ITEM -> 1
                    else -> 1
                }
            }
        }

        binding.list.layoutManager = manager

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
    }
}