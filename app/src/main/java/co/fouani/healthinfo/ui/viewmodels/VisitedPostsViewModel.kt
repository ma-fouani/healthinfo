package co.fouani.healthinfo.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import co.fouani.healthinfo.data.db.HealthDatabase
import co.fouani.healthinfo.data.db.VisitedPostsRepository
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.utils.Utility.exists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VisitedPostsViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Post>>

    var visited = false
    private val repository: VisitedPostsRepository

    init {
        val dao = HealthDatabase.getDatabase(application).visitedPostsDao()
        repository = VisitedPostsRepository(dao)
        readAllData = repository.readAllData
    }

    fun setPost(post: Post, lifecycleOwner: LifecycleOwner) {
        readAllData.observe(lifecycleOwner, { list ->
            if (exists(list)) {
                visited = list.find { post.id == it.id } != null
            }
        })
    }

    fun addVisitedPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVisitedPost(post)
        }
    }

    fun updateVisitedPostProgress(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVisitedProgress(post, post.progress)
        }
    }
}