package app.chayut.picsum.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_section_header.view.*

class HeaderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvSessionName: TextView = itemView.tv_session_name

}
