package live.lafi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import live.lafi.data.room.dao.ChatContentDao
import live.lafi.data.room.dao.ChatRoomDao
import live.lafi.data.room.dao.ChatRoomSystemRoleDao
import live.lafi.data.room.entity.ChatContentEntity
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.data.room.entity.ChatRoomSystemRoleEntity

@Database(
    entities = [
        ChatRoomEntity::class,
        ChatRoomSystemRoleEntity::class,
        ChatContentEntity::class
   ],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun chatRoomSystemRoleDao(): ChatRoomSystemRoleDao

    abstract fun chatContentDao(): ChatContentDao

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