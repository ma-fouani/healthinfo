package co.fouani.healthinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.fouani.healthinfo.data.models.Post

@Dao
interface VisitedPostsDAO {
    @Query("Update visited_post set progress = :new_progress where id =:id ")
    fun updateVisitedPost(id: String, new_progress: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVisitedPost(post: Post)

    @Query("SELECT * FROM visited_post ORDER BY id ASC")
    fun getAllVisitedPosts(): LiveData<List<Post>>

}