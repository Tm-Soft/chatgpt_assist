package live.lafi.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import live.lafi.domain.repository.LocalSettingRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LocalSettingRepositoryImpl @Inject constructor(
    private val context: Context
): LocalSettingRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    companion object {
        val CHAT_GPT_TOKEN = stringPreferencesKey("chatgpt_token")
        val MAX_USE_TOKEN = intPreferencesKey("max_use_token")
    }

    override suspend fun loadChatGptToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[CHAT_GPT_TOKEN] ?: ""
            }
    }

    override suspend fun saveChatGptToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[CHAT_GPT_TOKEN] = token
        }
    }

    override suspend fun saveMaxUseToken(number: Int) {
        context.dataStore.edit { preferences ->
            preferences[MAX_USE_TOKEN] = number
        }
    }

    override suspend fun loadMaxUseToken(): Flow<Int> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[MAX_USE_TOKEN] ?: 4096
            }
    }
}