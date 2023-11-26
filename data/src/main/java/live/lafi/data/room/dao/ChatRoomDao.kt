package live.lafi.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import live.lafi.data.room.entity.ChatRoomEntity

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room")
    fun getAll(): Flow<List<ChatRoomEntity>>

    @Query("SELECT * FROM chat_room WHERE chat_room_srl = :chatRoomSrl")
    fun getChatRoomEntity(chatRoomSrl: Long): ChatRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinEntity: ChatRoomEntity): Long

    @Query("DELETE FROM chat_room WHERE chat_room_srl = :chatRoomSrl")
    fun deleteWithSrl(chatRoomSrl: Long)
}