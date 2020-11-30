package com.example.leboncoinexamen.framework.presentation.album

import android.content.Context
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumAdapter(private val context: Context, private val listener: AlbumClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(AlbumDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
    }

    fun addHeaderAndSubmitList(list: List<Album>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.HeaderItem(0))
                else -> addheaders(list.map { DataItem.AlbumItem(it) })
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
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

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var header: TextView
        fun bind(albumId: Int) {
            header.text = header.context.getString(R.string.header_text, albumId.toString())
        }

        init {
            header = view.findViewById(R.id.text)
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView
        //var progress: ProgressBar

        fun bind(item: Album, clickListener: AlbumClickListener) {
            var img: ImageView? = image
            if (img == null) {
                img = ImageView(view.context)
            }

            Picasso.get().load(item.thumbnailUrl).placeholder(R.drawable.ic_launcher_foreground)
                .into(img)
            view.setOnClickListener { clickListener.onClick(item) }

        }

        init {
            image = view.findViewById(R.id.image)
            //progress = view.findViewById(R.id.progress)
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

    class AlbumClickListener(val clickListener: (album: Album) -> Unit) {
        fun onClick(album: Album) = clickListener(album)
    }

    fun addheaders(list: List<DataItem>): List<DataItem> {
        val mutList = list.toMutableList()
        val iterator = mutList.listIterator()
        var albumId: Int = Int.MIN_VALUE
        for (item in iterator) {
            item as DataItem.AlbumItem
            if (item.album.albumId != null && albumId != item.album.albumId) {
                albumId = item.album.albumId
                iterator.previous()
                iterator.add(DataItem.HeaderItem(item.album.albumId))
                iterator.next()
            }
        }

        return mutList.toList()
    }
}