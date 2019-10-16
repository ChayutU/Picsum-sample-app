package app.chayut.picsum.model

class LiveRecyclerViewType(type: Int) : BaseRecyclerViewItem(type) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_PIC = 1
    }
}