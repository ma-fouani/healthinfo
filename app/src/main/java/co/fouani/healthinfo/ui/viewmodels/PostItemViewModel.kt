package co.fouani.healthinfo.ui.viewmodels

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.navigation.findNavController
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.ui.fragments.HomeFragmentDirections
import co.fouani.healthinfo.utils.Utility

class PostItemViewModel(val post: Post) : BaseObservable() {
    fun getProgressVisibility(): Int {

        return if (post.progress <= 0) View.GONE else View.VISIBLE
    }

    fun onClick(view: View) {
        if (Utility.isNetworkAvailable(view.context)) {
            if (post.type == 2) {
                //it's a pdf
                view.findNavController().navigate(
                    HomeFragmentDirections.startPDFReader().setPost(post)
                )
            } else if (post.type == 1) {
                //it's a youtube video
                view.findNavController().navigate(
                    HomeFragmentDirections.startYouTubeFragment().setPost(post)
                )
            }
        } else {
            Toast.makeText(view.context,view.context.resources.getString(R.string.need_internet),Toast.LENGTH_LONG).show()
        }
    }

    fun inActive(view:View){
        Toast.makeText(view.context,"Feature coming soon", Toast.LENGTH_LONG).show()
    }
}