package com.example.leboncoinexamen.framework.presentation.photo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.leboncoinexamen.domain.model.Album


class PhotoPagerAdapter(fragment: Fragment) :
    FragmentStatePagerAdapter(
        fragment.childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    var images: List<Album> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Fragment {
        return PhotoFragment.newInstance(images.get(position))
    }
}