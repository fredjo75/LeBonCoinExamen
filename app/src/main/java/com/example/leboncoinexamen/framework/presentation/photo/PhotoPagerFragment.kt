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

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }

        return viewPager
    }


    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(
            context
        )
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {

                    val currentFragment = viewPager!!.adapter
                        ?.instantiateItem(
                            viewPager!!,
                            MainActivity.currentPosition
                        ) as Fragment
                    val view = currentFragment.view ?: return

                    sharedElements[names[0]] = view.findViewById(R.id.image)
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
    }
}