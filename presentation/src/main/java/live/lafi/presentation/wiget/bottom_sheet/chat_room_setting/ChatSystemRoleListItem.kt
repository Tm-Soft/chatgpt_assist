package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

data class ChatSystemRoleListItem(
    val viewType: ViewType,
    val chatSystemRoleSrl: Long,
    var roleContent: String,
) {
    enum class ViewType(val type: Int) {
        ROLE_CONTENT(0),
        PLUS_BUTTON(1)
    }
}