package co.fouani.healthinfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.fouani.healthinfo.data.models.Post

@Database(entities = [Post::class], version = 2, exportSchema = false)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun visitedPostsDao(): VisitedPostsDAO

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null
        fun getDatabase(context: Context): HealthDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}