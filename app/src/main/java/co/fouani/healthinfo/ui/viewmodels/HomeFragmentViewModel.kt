package co.fouani.healthinfo.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.fouani.healthinfo.BR
import co.fouani.healthinfo.data.db.HealthDatabase
import co.fouani.healthinfo.data.db.VisitedPostsRepository
import co.fouani.healthinfo.data.models.GrandProgress
import co.fouani.healthinfo.data.models.Page
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.utils.Utility.exists
import co.fouani.healthinfo.utils.Utility.updatePostVisit
import co.fouani.octipulse.data.api.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(val context: Context) : BaseObservable() {
    var page = MutableLiveData<Page>()
    var grandProgress = MutableLiveData<GrandProgress>()
    val readAllData: LiveData<List<Post>>
    private val repository: VisitedPostsRepository
    var grandProgressAdded: Boolean = false

    init {
        val dao = HealthDatabase.getDatabase(context).visitedPostsDao()
        repository = VisitedPostsRepository(dao)
        readAllData = repository.readAllData
    }

    @Bindable
    var processing = false

    fun loadPage(lifecycleOwner: LifecycleOwner) {
        val apiClient = APIClient(context)
        processing = true
        notifyPropertyChanged(BR.processing)
        apiClient.getPosts()!!.enqueue(object : Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val newPage = Page()
                if (exists(response.body())) {
                    readAllData.observe(lifecycleOwner, { list ->
                        if (exists(list)) {
                            val progress = GrandProgress(0, 0)
                            for (visited: Post in list) {
                                updatePostVisit(
                                    response.body()!!.find { visited.id == it.id },
                                    visited
                                )
                                newPage.watchList.add(visited)
                                if (visited.total > 0) {
                                    progress.progress += visited.progress * 100 / visited.total
                                }
                            }
                            progress.total = response.body()!!.size * 100
                            grandProgress.value = progress
                            page.value = newPage
                        }
                    })

                    for (post: Post in response.body()!!) {
                        when (post.section) {
                            1 -> newPage.section1.add(post)
                            2 -> newPage.section2.add(post)
                            3 -> newPage.section3.add(post)
                        }
                    }
                    page.value = newPage
                    processing = false
                    notifyPropertyChanged(BR.processing)
                } else {
                    processing = false
                    notifyPropertyChanged(BR.processing)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                processing = false
                notifyPropertyChanged(BR.processing)
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

}