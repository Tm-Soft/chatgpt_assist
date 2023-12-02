package live.lafi.domain.usecase.chat

import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.repository.ChatRepository

class InsertChatRoomSystemRoleListUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        list: List<ChatRoomSystemRoleInfo>
    ) {
        chatRepository.insertChatRoomSystemRoleList(
            list = list
        )
    }
}