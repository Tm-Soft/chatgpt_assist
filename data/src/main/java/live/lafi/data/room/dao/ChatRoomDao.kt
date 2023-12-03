package live.lafi.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.util.enums.ChatRoomType

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_room")
    fun getAll(): Flow<List<ChatRoomEntity>>

    @Query("SELECT * FROM chat_room WHERE chat_room_type = :chatRoomType")
    fun getAll(chatRoomType: Int): Flow<List<ChatRoomEntity>>

    @Query("SELECT * FROM chat_room WHERE chat_room_srl = :chatRoomSrl")
    fun getChatRoomEntity(chatRoomSrl: Long): ChatRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatRoomEntity: ChatRoomEntity): Long

    @Query("UPDATE chat_room SET chat_room_title = :title WHERE chat_room_srl = :chatRoomSrl")
    fun updateChatRoomTitle(chatRoomSrl: Long, title: String)

    @Query("DELETE FROM chat_room WHERE chat_room_srl = :chatRoomSrl")
    fun deleteWithSrl(chatRoomSrl: Long)
}