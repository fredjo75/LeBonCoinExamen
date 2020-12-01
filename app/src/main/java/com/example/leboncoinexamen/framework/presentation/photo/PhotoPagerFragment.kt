package com.example.leboncoinexamen.framework.presentation.photo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import androidx.viewpager.widget.ViewPager
import com.example.leboncoinexamen.R
import com.example.leboncoinexamen.framework.presentation.BaseFragment
import com.example.leboncoinexamen.framework.presentation.MainActivity
import javax.inject.Inject


/**
 * A fragment for displaying a pager of images.
 */
class PhotoPagerFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private var viewPager: ViewPager? = null
    private lateinit var viewModel: PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPager = inflater.inflate(R.layout.fragment_photo_pager, container, false) as ViewPager?
        viewPager!!.adapter = PhotoPagerAdapter(this)

        viewModel = viewModelProvider.create(PhotoViewModel::class.java)
        viewModel.response.observe(viewLifecycleOwner, {
            if (it !== null) {
                (viewPager!!.adapter as PhotoPagerAdapter).images = it
                viewPager!!.currentItem = MainActivity.currentPosition
            }
        })

        viewPager!!.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                MainActivity.currentPosition = position
            }
        })
        prepareSharedElementTransition()

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }

        return viewPager
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(
            context
        )
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // Locate the image view at the primary fragment (the ImageFragment that is currently
                    // visible). To locate the fragment, call instantiateItem with the selection position.
                    // At this stage, the method will simply return the fragment at the position and will
                    // not create a new one.
                    val currentFragment = viewPager!!.adapter
                        ?.instantiateItem(
                            viewPager!!,
                            MainActivity.currentPosition
                        ) as Fragment
                    val view = currentFragment.view ?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = view.findViewById(R.id.image)
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
    }
}