package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class InsertChatRoomUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(
        title: String,
        profileUri: String?,
    ) {
        chatRepository.insertChatRoom(
            title = title,
            profileUri = profileUri,
        )
    }
}