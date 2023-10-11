package live.lafi.util

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.ModelType
import live.lafi.util.model.ChatGptMessage
import timber.log.Timber

object GetGptToken {
    operator fun invoke(
        modelType: GptModelType,
        messages: List<ChatGptMessage>
    ): Int {
        var tokenPerMessage = 0
        var tokenPerName = 0
        var realModelType: ModelType? = null

        when (modelType) {
            GptModelType.GPT_3_5_TURBO -> {
                tokenPerMessage = 3
                tokenPerName = -1
                realModelType = ModelType.GPT_3_5_TURBO
            }

            GptModelType.GPT_4 -> {
                tokenPerMessage = 3
                tokenPerName = 1
                realModelType = ModelType.GPT_4
            }

            else -> {
            throw IllegalArgumentException("Unsupported model: " + modelType)
            }
        }
        Timber.tag("test__").d("내부 시작")
        val encoding = Encodings.newDefaultEncodingRegistry().getEncodingForModel(realModelType)

        var sum  = 0
        messages.forEach { message ->
            sum += tokenPerMessage
            sum += encoding.countTokens(message.role)
            sum += encoding.countTokens(message.content)
        }
        sum += 3

        return sum
    }

    enum class GptModelType {
        GPT_3_5_TURBO,
        GPT_4
    }
}