package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class GetAllChatRoomWithChatRoomTypeUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomType: Int) = chatRepository.getAllChatRoom(chatRoomType)
}