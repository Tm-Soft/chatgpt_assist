package live.lafi.presentation.chat_room_list

data class ChatRoomInfo(
    val chatRoomSrl: Long,
    val title: String,
    val question: String,
    val content: String,
    val profileUri: String,
    val lastViewDate: Long,
    val lastUpdate: Long
)
