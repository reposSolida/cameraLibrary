package com.camera.view.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.camera.viewModel.OnBtnPressListener
import com.mylibrary.R

class PhotoPagerAdapter(
    private val bitmaps: MutableList<Bitmap>,
    private val listener: OnBtnPressListener
) : RecyclerView.Adapter<PhotoPagerAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_image_corousel_layout, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.imageView.setImageBitmap(bitmaps[position])
        holder.imageView.setOnClickListener {
            listener.buttonPress(false, position)
        }
    }

    override fun getItemCount(): Int {
        return bitmaps.size
    }

    fun clearList() {
        bitmaps.clear()
    }
}
