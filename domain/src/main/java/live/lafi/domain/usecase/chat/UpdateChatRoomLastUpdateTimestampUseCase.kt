package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class UpdateChatRoomLastUpdateTimestampUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomSrl: Long, lastUpdateTimestamp: Long) {
        chatRepository.updateChatRoomLastUpdateTimestamp(chatRoomSrl, lastUpdateTimestamp)
    }
}