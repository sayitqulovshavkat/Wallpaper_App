package com.mobil.wallpaper_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobil.wallpaper_app.databinding.ItemImageRvBinding
import com.mobil.wallpaper_app.model.ImageModel
import com.squareup.picasso.Picasso

class LikeAdapter(val list: List<ImageModel>, val listener: OnImageClickListener) :
    RecyclerView.Adapter<LikeAdapter.Vh>() {


    inner class Vh(private val itemRvBinding: ItemImageRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(imageModel: ImageModel) {
            itemRvBinding.apply {
                Picasso.get().load(imageModel.urls.thumb).into(image)
            }
            itemView.setOnClickListener {
                listener.onImageClick(imageModel)
            }
        }
    }


    interface OnImageClickListener {
        fun onImageClick(imageModel: ImageModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemImageRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


}