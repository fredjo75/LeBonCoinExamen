package com.example.leboncoinexamen.framework.presentation.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.leboncoinexamen.R
import com.example.leboncoinexamen.domain.model.Album
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.String


/**
 * A fragment for displaying an image.
 */
class PhotoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_photo, container, false)
        val arguments = arguments
        val photo = arguments!!.getSerializable(KEY_PHOTO) as Album
        val imageView = view.findViewById<View>(R.id.image) as ImageView

        // Just like we do when binding views at the grid, we set the transition name to be the string
        // value of the image res.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageView.transitionName = String.valueOf(photo.id)
        }
        Picasso.get().load(photo.thumbnailUrl).into(imageView)
        Picasso.get().load(photo.url).into(imageView, object : Callback {
            override fun onSuccess() {
                parentFragment?.startPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                parentFragment?.startPostponedEnterTransition()
            }

        })
        return view
    }

    companion object {
        private const val KEY_PHOTO = "photo"
        fun newInstance(photo: Album): PhotoFragment {
            val fragment = PhotoFragment()
            val argument = Bundle()
            argument.putSerializable(KEY_PHOTO, photo)
            fragment.arguments = argument
            return fragment
        }
    }
}