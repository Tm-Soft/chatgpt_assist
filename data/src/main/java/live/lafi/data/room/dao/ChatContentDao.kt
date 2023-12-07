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

    @Query("SELECT * FROM chat_content WHERE status = 'wait' AND role = 'user' ORDER BY create_date ASC LIMIT 1")
    fun getChatContentWaitMessage(): Flow<ChatContentEntity>

    @Query("SELECT * FROM chat_content WHERE chat_room_srl = :chatRoomSrl ORDER BY create_date ASC")
    fun getChatContentListWithChatRoomSrl(chatRoomSrl: Long): List<ChatContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatContentEntity: ChatContentEntity): Long

    @Query("UPDATE chat_content SET status = :status, update_date = :updateDate WHERE chat_content_srl = :chatContentSrl")
    fun updateChatContentStatus(chatContentSrl: Long, status: String, updateDate: Long)

    @Query("DELETE FROM chat_content WHERE chat_room_srl = :chatRoomSrl")
    fun deleteChatContentWithChatRoomSrl(chatRoomSrl: Long)
}