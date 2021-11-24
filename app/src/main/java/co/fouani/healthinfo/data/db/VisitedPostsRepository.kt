package co.fouani.healthinfo.data.db

import androidx.lifecycle.LiveData
import co.fouani.healthinfo.data.models.Post

class VisitedPostsRepository(private val dao: VisitedPostsDAO) {
    val readAllData: LiveData<List<Post>> = dao.getAllVisitedPosts()
    fun addVisitedPost(post: Post) {
        dao.addVisitedPost(post)
    }

    fun updateVisitedProgress(post: Post, progress: Int) {
        dao.updateVisitedPost(post.id, progress)
    }

}