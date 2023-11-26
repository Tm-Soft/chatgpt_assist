package live.lafi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import live.lafi.data.room.dao.ChatRoomDao
import live.lafi.data.room.entity.ChatRoomEntity

@Database(
    entities = [ChatRoomEntity::class],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao

    companion object {
        private var INSTANT: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            if (INSTANT == null) {
                synchronized(ChatDatabase::class.java) {
                    INSTANT = Room.databaseBuilder(
                        context.applicationContext,
                        ChatDatabase::class.java,
                        "ChatDatabase"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                }
            }

            return INSTANT!!
        }
    }
}