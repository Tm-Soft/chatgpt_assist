package live.lafi.domain.usecase.local_setting

import live.lafi.domain.repository.LocalSettingRepository

class SaveMaxUseTokenUseCase(private val localSettingRepository: LocalSettingRepository) {
    suspend operator fun invoke(number: Int) {
        localSettingRepository.saveMaxUseToken(number)
    }
}