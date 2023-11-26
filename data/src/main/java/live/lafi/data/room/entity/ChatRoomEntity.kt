package live.lafi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room")
data class ChatRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_room_srl") val chatRoomSrl: Long,
    @ColumnInfo(name = "chat_room_title") val chatRoomTitle: String,
    @ColumnInfo(name = "profile_uri") val profileUri: String?,
    @ColumnInfo(name = "last_update_timestamp") val lastUpdateTimestamp: Long?,
    @ColumnInfo(name = "last_read_timestamp") val lastReadTimestamp: Long?,
)