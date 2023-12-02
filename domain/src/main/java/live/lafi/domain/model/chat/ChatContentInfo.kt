package live.lafi.domain.model.chat

data class ChatContentInfo(
    val chatContentSrl: Long,
    val chatRoomSrl: Long,
    val parentChatContentSrl: Long,
    val role: String,
    val content: String,
    val contentSummary: String,
    val contentTranslate: String,
    val useToken: Int,
    val status: String,
    val updateDate: Long,
    val createDate: Long
)