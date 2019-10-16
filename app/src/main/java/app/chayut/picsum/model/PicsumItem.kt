package app.chayut.picsum.model

data class PicsumItem(
    val author: String? = null,
    val id: String? = null,
    val url: String? = null
) : BaseRecyclerViewItem(LiveRecyclerViewType.TYPE_PIC)
