package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class InsertChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomType: Int,
        title: String,
        profileUri: String?,
    ) = chatRepository.insertChatRoom(
        chatRoomType = chatRoomType,
        title = title,
        profileUri = profileUri,
    )
}