package live.lafi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import live.lafi.data.room.entity.ChatContentEntity

@Dao
interface ChatContentDao {
    @Query("SELECT * FROM chat_content")
    fun getAll(): Flow<List<ChatContentEntity>>

    @Query("SELECT * FROM chat_content WHERE chat_room_srl = :chatRoomSrl ")
    fun getAllWithChatRoomSrl(chatRoomSrl: Long): Flow<List<ChatContentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatContentEntity: ChatContentEntity): Long
}