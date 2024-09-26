package com.example.testapp.presentation.ui.gallery.adapter

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.domain.model.ImageWithBitmap

interface OnImageClickListener {
    fun onImageClick(position: Int) {
    }
}

class ImageRecyclerViewAdapter(
    private val listener: OnImageClickListener
) :
    RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {

    private val differ: AsyncListDiffer<ImageWithBitmap> = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: MutableList<ImageWithBitmap>) {
        differ.submitList(list)
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val textView = holder.textView
        val imageWithBitmap = differ.currentList[position]
        textView.text = imageWithBitmap.date
        textView.setOnClickListener {
            listener.onImageClick(position)
        }
        textView.setOnLongClickListener {
            true
        }
        val bitmapDrawable = BitmapDrawable(holder.itemView.context.resources, imageWithBitmap.bitmap)
        textView.setCompoundDrawablesWithIntrinsicBounds(null, bitmapDrawable, null, null)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageWithBitmap>() {
            override fun areItemsTheSame(oldImage: ImageWithBitmap, newImage: ImageWithBitmap): Boolean {
                return oldImage.id == newImage.id
            }

            override fun areContentsTheSame(oldImage: ImageWithBitmap, newImage: ImageWithBitmap): Boolean {
                return oldImage == newImage
            }
        }
    }
}