package live.lafi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room_system_role")
data class ChatRoomSystemRoleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_room_system_role_srl") val chatRoomSystemRoleSrl: Long,
    @ColumnInfo(name = "chat_room_srl") val chatRoomSrl: Long,
    @ColumnInfo(name = "role_content") val roleContent: String
)
