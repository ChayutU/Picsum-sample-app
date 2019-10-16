package app.chayut.picsum.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.chayut.picsum.model.BaseRecyclerViewItem
import app.chayut.picsum.model.PicItemDao
import app.chayut.picsum.model.PicsumItem
import app.chayut.picsum.model.SessionHeaderItem
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson


class MainViewModel : ViewModel() {

    val picList: MutableLiveData<List<BaseRecyclerViewItem>> by lazy {
        MutableLiveData<List<BaseRecyclerViewItem>>()
    }

    fun getPicsumData(newRequestQueue: RequestQueue) {
        val list = ArrayList<BaseRecyclerViewItem>()

        list.add(SessionHeaderItem("Sample picture"))

        val url = "https://picsum.photos/v2/list"
        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> {
                val daoList = Gson().fromJson(it, Array<PicItemDao>::class.java)
                daoList.map { dao ->
                    list.add(PicsumItem(
                        author = dao.author,
                        id = dao.id,
                        url = dao.url
                    ))
                }
                picList.postValue(list)
            }, Response.ErrorListener { })

        newRequestQueue.add(stringRequest)
    }
}
