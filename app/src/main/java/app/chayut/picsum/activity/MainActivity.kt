package app.chayut.picsum.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import app.chayut.picsum.R
import app.chayut.picsum.adapter.PicRecyclerViewAdapter
import app.chayut.picsum.model.BaseRecyclerViewItem
import app.chayut.picsum.model.LiveRecyclerViewType
import app.chayut.picsum.viewModel.MainViewModel
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: GridLayoutManager
    lateinit var viewModel: MainViewModel
    private val mRecyclerViewAdapter: PicRecyclerViewAdapter = PicRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        setupObserve()
    }

    private fun setupRecyclerView() {
        val mLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = onSpanSizeLookup
        }

        layoutManager = mLayoutManager
        mLayoutManager.spanSizeLookup = onSpanSizeLookup

        recycler_main.layoutManager = mLayoutManager

        recycler_main.adapter = mRecyclerViewAdapter
    }

    private fun setupObserve() {
        viewModel.apply {
            picList.observe(this@MainActivity, Observer {
                mRecyclerViewAdapter.setRecyclerItem(it as MutableList<BaseRecyclerViewItem>)
                mRecyclerViewAdapter.notifyDataSetChanged()
            })

            getPicsumData(Volley.newRequestQueue(this@MainActivity))
        }
    }

    private var onSpanSizeLookup: GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mRecyclerViewAdapter.getItemViewType(position)) {
                    LiveRecyclerViewType.TYPE_HEADER -> 2
                    else -> 1
                }
            }
        }
}
