package app.chayut.picsum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.chayut.picsum.*
import app.chayut.picsum.model.BaseRecyclerViewItem
import app.chayut.picsum.model.LiveRecyclerViewType
import app.chayut.picsum.model.PicsumItem
import app.chayut.picsum.model.SessionHeaderItem
import app.chayut.picsum.viewHolder.HeaderItemViewHolder
import app.chayut.picsum.viewHolder.PicItemViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*

class PicRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recyclerList: MutableList<BaseRecyclerViewItem>? = null

    init {
        recyclerList = ArrayList()
    }

    override fun getItemCount(): Int {
        return recyclerList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return recyclerList?.get(position)?.type ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LiveRecyclerViewType.TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_section_header, parent, false)
            return HeaderItemViewHolder(view)
        }
        if (viewType == LiveRecyclerViewType.TYPE_PIC) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_pic, parent, false)
            return PicItemViewHolder(view)
        }
        throw NullPointerException("View Type $viewType doesn't match with any existing order detail type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerViewItem = recyclerList?.get(position)

        when (holder) {
            is HeaderItemViewHolder -> {
                val headerItem = recyclerViewItem as SessionHeaderItem

                setupHeader(holder, headerItem)
            }
            is PicItemViewHolder -> {
                val picItem = recyclerViewItem as PicsumItem

                setupPicsum(holder, picItem)
            }
        }
    }

    fun setRecyclerItem(newItemList: MutableList<BaseRecyclerViewItem>) =
        if (recyclerList == null) {
            recyclerList = newItemList
            notifyItemRangeInserted(0, newItemList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return recyclerList?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return newItemList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = recyclerList?.get(oldItemPosition)
                    val newItem = newItemList[newItemPosition]

                    if (oldItem?.type == newItem.type) {
                        return oldItem == newItem
                    }
                    return false
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem = recyclerList?.get(oldItemPosition)
                    val newItem = newItemList[newItemPosition]
                    if (oldItem?.type == newItem.type) {
                        return oldItem == newItem
                    }

                    return false
                }
            })
            recyclerList = newItemList
            result.dispatchUpdatesTo(this)
        }

    //------------------------------------------

    private fun setupHeader(holder: HeaderItemViewHolder, headerItem: SessionHeaderItem) {
        holder.tvSessionName.text = headerItem.title
    }

    private fun setupPicsum(holder: PicItemViewHolder, picItem: PicsumItem) {
        holder.apply {

            Glide.with(ivPicsum)
                .load(picItem.url)
                .apply(
                    RequestOptions
                        .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                )
                .into(ivPicsum)

            tvAuthor.text = picItem.author
        }
    }
}