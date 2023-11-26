package live.lafi.domain.model.chat

data class ChatRoomInfo(
    val chatRoomSrl: Long,
    val title: String,
    val profileUri: String,
    val lastReadTimestamp: Long,
    val lastUpdateTimestamp: Long
)
