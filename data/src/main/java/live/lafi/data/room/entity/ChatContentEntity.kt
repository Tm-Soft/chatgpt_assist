package live.lafi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_content")
data class ChatContentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_content_srl") val chatContentSrl: Long,
    @ColumnInfo(name = "chat_room_srl") val chatRoomSrl: Long,
    @ColumnInfo(name = "parent_chat_content_srl") val parentChatContentSrl: Long?,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "content_summary") val contentSummary: String?,
    @ColumnInfo(name = "content_translate") val contentTranslate: String?,
    @ColumnInfo(name = "use_token") val useToken: Int?,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "update_date") val updateDate: Long,
    @ColumnInfo(name = "create_date") val createDate: Long,
)