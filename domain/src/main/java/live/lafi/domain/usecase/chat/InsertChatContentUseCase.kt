package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class InsertChatContentUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long,
        parentChatContentSrl: Long? = null,
        role: String,
        content: String,
        useToken: Int? = null,
        status: String,
        updateDate: Long,
        createDate: Long
    ) {
        chatRepository.insertChatContent(
            chatRoomSrl = chatRoomSrl,
            parentChatContentSrl = parentChatContentSrl,
            role = role,
            content = content,
            useToken = useToken,
            status = status,
            updateDate = updateDate,
            createDate = createDate
        )
    }
}