package app.chayut.picsum.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pic.view.*

class PicItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvAuthor: TextView = itemView.tv_author
    var ivPicsum: ImageView = itemView.iv_picsum
}
