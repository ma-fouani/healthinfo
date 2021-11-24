package co.fouani.healthinfo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "visited_post")
data class Post(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val section: Int,
    val duration: String,
    val thumbnail: String,
    val type: Int,
    val url: String,
    var total:Int,
    var progress:Int
) : Serializable