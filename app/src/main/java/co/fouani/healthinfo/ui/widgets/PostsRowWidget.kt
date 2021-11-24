package co.fouani.healthinfo.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.databinding.WidgetBinding
import co.fouani.healthinfo.utils.Utility.exists
import co.fouani.healthinfo.utils.Utility.getSection

class PostsRowWidget(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    var binding: WidgetBinding

    fun setWidget(posts: List<Post>, title:String): PostsRowWidget {
        if (exists(posts)) {
            binding.widgetTitle =  title
            binding.recycler.isNestedScrollingEnabled = false
            binding.recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recycler.adapter =
                PostsAdapter(posts as MutableList<Post>)
            binding.recycler.adapter!!.notifyItemRangeChanged(0, posts.size - 1)
        }
        return this
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(
                layoutInflater, R.layout.widget,
                parent as ViewGroup?, false
            )
        this.addView(binding.root)
    }

}