package live.lafi.presentation.chat_room_list

data class ChatRoomItem(
    val chatRoomSrl: Long,
    val title: String,
    val question: String,
    val content: String,
    val profileUri: String,
    val lastReadTimestamp: Long,
    val lastUpdateTimestamp: Long
)
