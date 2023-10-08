package live.lafi.domain.usecase.local_setting

import live.lafi.domain.repository.LocalSettingRepository

class SaveChatGptTokenUseCase(private val localSettingRepository: LocalSettingRepository) {
    suspend operator fun invoke(token: String) {
        localSettingRepository.updateChatGptToken(token)
    }
}