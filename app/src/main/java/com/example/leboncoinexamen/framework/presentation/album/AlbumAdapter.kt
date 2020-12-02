package com.example.leboncoinexamen.framework.presentation.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leboncoinexamen.R
import com.example.leboncoinexamen.domain.model.Album
import com.squareup.picasso.Picasso
import java.lang.String

class AlbumAdapter(private val listener: AlbumClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(AlbumDiffCallback()) {

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val albumItem = getItem(position) as DataItem.AlbumItem
                holder.bind(albumItem.album, listener)
            }
            is TextViewHolder -> {
                val nightItem = getItem(position) as DataItem.HeaderItem
                holder.bind(nightItem.albumId)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is DataItem.AlbumItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var header: TextView = view.findViewById(R.id.text)
        fun bind(albumId: Int) {
            header.text = header.context.getString(R.string.header_text, albumId.toString())
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var image: ImageView = view.findViewById(R.id.image)

        fun bind(item: Album, clickListener: AlbumClickListener) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                image.transitionName = String.valueOf(item.id)
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Picasso.get().load(item.thumbnailUrl).placeholder(R.drawable.ic_launcher_foreground)
                    .into(image)
            } else {
                Picasso.get().load(item.thumbnailUrl).placeholder(R.drawable.ic_android)
                    .into(image)
            }
            view.setOnClickListener { clickListener.onClick(image, item) }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                layoutInflater.inflate(R.layout.item_album, parent, false)
                val view = layoutInflater.inflate(R.layout.item_album, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class AlbumClickListener(val clickListener: (view: View, album: Album) -> Unit) {
        fun onClick(view: View, album: Album) = clickListener(view, album)
    }
}