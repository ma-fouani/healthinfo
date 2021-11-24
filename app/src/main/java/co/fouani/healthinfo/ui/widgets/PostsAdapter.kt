package co.fouani.healthinfo.ui.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.fouani.healthinfo.BR
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.ui.viewmodels.PostItemViewModel

class PostsAdapter(private val data: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                R.layout.item_list,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = data[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            val postViewModel = PostItemViewModel(post)
            binding.setVariable(BR.postViewModel, postViewModel)
            binding.executePendingBindings()
        }
    }
}