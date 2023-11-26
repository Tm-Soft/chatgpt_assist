package live.lafi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import live.lafi.data.room.entity.ChatRoomEntity

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room")
    fun getAll(): Flow<List<ChatRoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinEntity: ChatRoomEntity)
}