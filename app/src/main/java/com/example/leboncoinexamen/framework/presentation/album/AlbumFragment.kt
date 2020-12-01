package com.example.leboncoinexamen.framework.presentation.album

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.example.leboncoinexamen.R
import com.example.leboncoinexamen.databinding.FragmentAlbumListBinding
import com.example.leboncoinexamen.databinding.FragmentAlbumListBindingImpl
import com.example.leboncoinexamen.domain.model.Album
import com.example.leboncoinexamen.framework.presentation.BaseFragment
import com.example.leboncoinexamen.framework.presentation.MainActivity
import javax.inject.Inject


class AlbumFragment : BaseFragment() {

    private var columnCount = 1

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var binding: FragmentAlbumListBinding
    private lateinit var viewModel: AlbumViewModel
    private var mListState: Parcelable? = null

    companion object {
        private var PORTRAIT_COLUMN_COUNT = 4
        private var LANDSCAPE_COLUMN_COUNT = 8
        val BUNDLE_KEY = "bundleKey"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumListBindingImpl.inflate(inflater)
        viewModel = viewModelProvider.create(AlbumViewModel::class.java)
        mListState = savedInstanceState?.getParcelable(BUNDLE_KEY)
        binding.viewModel = viewModel

        val albumAdapter = AlbumAdapter(
            requireContext(),
            AlbumAdapter.AlbumClickListener { view: View, album: Album ->
                run {

                    MainActivity.currentPosition = viewModel.response.value!!.indexOf(album)
                    val action =
                        AlbumFragmentDirections.actionAlbumFragmentToPhotoPagerFragment(album)

                    (this@AlbumFragment.exitTransition as TransitionSet).excludeTarget(view, true)

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        val extras = FragmentNavigatorExtras(view to view.transitionName)
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(
                                action,
                                extras
                            )
                    } else {
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(
                                action
                            )
                    }
                }
            })

        binding.list.adapter = albumAdapter

        binding.lifecycleOwner = this

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                albumAdapter.addHeaderAndSubmitList(it)
                if(mListState!=null) {
                    binding.list.layoutManager?.onRestoreInstanceState(mListState)
                    binding.list.adapter?.notifyDataSetChanged()
                }else{
                    scrollToCurrentPosition()
                }
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
        prepareTransitions()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //scrollToCurrentPosition()
    }

    override fun onResume() {
        super.onResume()
    }
    private fun getCurrentPosition(): Int {
        val list = viewModel.response.value
        if (!list.isNullOrEmpty()) {
            return list[MainActivity.currentPosition].albumId?.plus(
                MainActivity.currentPosition
            )!!
        }
        return -1
    }

    /*  private fun fromAdapterPosition(position: ): Int {
          val list = viewModel.response.value
          if (!list.isNullOrEmpty()) {
              MainActivity.currentPosition = list[position].albumId?.plus(
                  MainActivity.currentPosition
              )!!
          }
      }*/

    private fun scrollToCurrentPosition() {

        val pos: Int = getCurrentPosition()
        if (pos < 0) {
            return
        }
        binding.list.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                binding.list.removeOnLayoutChangeListener(this)
                val layoutManager: RecyclerView.LayoutManager = binding.list.layoutManager!!
                val viewAtPosition = layoutManager.findViewByPosition(pos)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    binding.list.post { layoutManager.scrollToPosition(pos) }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(BUNDLE_KEY, binding.list.layoutManager?.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
    }

    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder: RecyclerView.ViewHolder = binding.list
                        .findViewHolderForAdapterPosition(getCurrentPosition())
                        ?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] =
                        selectedViewHolder.itemView.findViewById(R.id.image)
                }
            })
    }
}