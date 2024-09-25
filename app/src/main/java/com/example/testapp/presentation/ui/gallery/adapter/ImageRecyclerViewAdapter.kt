package com.example.testapp.presentation.ui.gallery.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.domain.model.ImageWithBitmap

interface OnImageClickListener {
    fun onImageClick(position: Int) {

    }
}

class ImageRecyclerViewAdapter(
    private var images: MutableList<ImageWithBitmap>,
    private val listener: OnImageClickListener
) :
    RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val textView = holder.textView
        textView.text = images[position].date
        textView.setOnClickListener {
            listener.onImageClick(position)
        }
        textView.setOnLongClickListener {
            true
        }

        val bitmapDrawable = BitmapDrawable(holder.itemView.context.resources, images[position].bitmap)
        textView.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable, null, null)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateImages(newImages: MutableList<ImageWithBitmap>) {
        images = newImages
        notifyDataSetChanged()
    }
}