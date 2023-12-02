package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class InsertChatContentUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long,
        parentChatContentSrl: Long? = null,
        role: String,
        content: String,
        contentSummary: String? = null,
        contentTranslate: String? = null,
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
            contentSummary = contentSummary,
            contentTranslate = contentTranslate,
            useToken = useToken,
            status = status,
            updateDate = updateDate,
            createDate = createDate
        )
    }
}