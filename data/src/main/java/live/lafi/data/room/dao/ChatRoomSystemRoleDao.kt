package live.lafi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.data.room.entity.ChatRoomSystemRoleEntity

@Dao
interface ChatRoomSystemRoleDao {
    @Query("SELECT * FROM chat_room_system_role WHERE chat_room_srl = :chatRoomSrl")
    fun getChatRoomSystemRoleList(chatRoomSrl: Long): Flow<List<ChatRoomSystemRoleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatRoomSystemRoleEntity: ChatRoomSystemRoleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<ChatRoomSystemRoleEntity>): List<Long>

    @Update
    fun update(chatRoomSystemRoleEntity: ChatRoomSystemRoleEntity)

    @Update
    fun updateList(chatRoomSystemRoleEntity: List<ChatRoomSystemRoleEntity>)

    @Query("DELETE FROM chat_room_system_role WHERE chat_room_system_role_srl = :chatRoomSystemRoleSrl")
    fun deleteWithSystemRoleSrl(chatRoomSystemRoleSrl: Long)

    @Query("DELETE FROM chat_room_system_role WHERE chat_room_srl = :chatRoomSrl")
    fun deleteWithSystemRoleSrlWithChatRoomSrl(chatRoomSrl: Long)
}