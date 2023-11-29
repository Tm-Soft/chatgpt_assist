package live.lafi.presentation.chat_room

data class ChatContentItem(
    val viewType: ViewType,
    val chatContentSrl: Long,
    val content: String,
    val profileUri: String,
    val nickname: String,
    val createDate: Long,
) {
    enum class ViewType(val type: Int) {
        CHAT_CONTENT_MY_TEXT(0),
        CHAT_CONTENT_OTHER_TEXT(1)
    }
}