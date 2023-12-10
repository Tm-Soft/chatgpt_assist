package live.lafi.util.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import live.lafi.domain.ApiResult.LoadingStart.onError
import live.lafi.domain.ApiResult.LoadingStart.onException
import live.lafi.domain.ApiResult.LoadingStart.onSuccess
import live.lafi.domain.model.chat.ChatContentInfo
import live.lafi.domain.usecase.chat.DeleteChatContentWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.GetChatContentListWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.GetChatContentWaitMessageUseCase
import live.lafi.domain.usecase.chat.GetChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.InsertChatContentUseCase
import live.lafi.domain.usecase.chat.UpdateChatContentStatusUseCase
import live.lafi.domain.usecase.chat_gpt.PostChatListCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.util.DateUtil
import live.lafi.util.public_model.ContentManager
import live.lafi.util.public_model.GptTokenManager
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ChatContentService : Service() {
    @Inject
    lateinit var getChatContentWaitMessageUseCase: GetChatContentWaitMessageUseCase
    @Inject
    lateinit var updateChatContentStatusUseCase: UpdateChatContentStatusUseCase
    @Inject
    lateinit var postChatListCompletionsUseCase: PostChatListCompletionsUseCase
    @Inject
    lateinit var insertChatContentUseCase: InsertChatContentUseCase
    @Inject
    lateinit var getChatRoomSystemRoleUseCase: GetChatRoomSystemRoleUseCase
    @Inject
    lateinit var getChatContentListWithChatRoomSrlUseCase: GetChatContentListWithChatRoomSrlUseCase
    @Inject
    lateinit var loadChatGptTokenUseCase: LoadChatGptTokenUseCase

    private val coroutineJob = SupervisorJob()

    private val scopeMain = CoroutineScope(Dispatchers.Main + coroutineJob)
    private val scopeIO = CoroutineScope(Dispatchers.IO + coroutineJob)
    private val scopeDefault = CoroutineScope(Dispatchers.Default + coroutineJob)

    override fun onCreate() {
        super.onCreate()

        Timber.tag("ChatContentService").d("Start ChatContent Service")
        scopeIO.launch {
            val token = loadChatGptTokenUseCase().first()
            if (token.isNotEmpty()) {
                GptTokenManager.editToken(token)
            }
        }
        subscribe()

        ContentManager.setContentServiceRunning(true)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Timber.tag("ChatContentService").d("Destroy Service")
        ContentManager.setContentServiceRunning(false)
        coroutineJob.cancel()
        super.onDestroy()
    }

    private fun subscribe() {
        scopeIO.launch {
            getChatContentWaitMessageUseCase()
                .filterNotNull()
                .collect { chatContentInfo ->
                    if (chatContentInfo.chatContentSrl != 0L && !ContentManager.isExistRequestContentSrl(chatContentInfo.chatContentSrl)) {
                        ContentManager.addRequestContent(
                            contentSrl = chatContentInfo.chatContentSrl
                        )

                        val beforeChatContentList = getChatContentListWithChatRoomSrlUseCase(
                            chatRoomSrl = chatContentInfo.chatRoomSrl
                        ).filter { it.chatContentSrl != chatContentInfo.chatContentSrl && it.status == "complete" }

                        val mappedPairs = mutableListOf<ChatContentInfo>()
                        beforeChatContentList.forEach { chatContent ->
                            if (chatContent.parentChatContentSrl == 0L && chatContent.chatContentSrl < chatContentInfo.chatContentSrl) { // 질문인 경우
                                val answer = beforeChatContentList.find { it.parentChatContentSrl == chatContent.chatContentSrl }
                                if (answer != null) {
                                    mappedPairs.add(chatContent) // 질문 추가
                                    mappedPairs.add(answer) // 해당 답변 추가
                                }
                            }
                        }

                        val sendMessage = mappedPairs.map { Pair(it.role, it.content) } + Pair("user", chatContentInfo.content)
                        Timber.tag("ChatContentService").d("chat Content Data : $chatContentInfo\n전송 대화 : ${sendMessage}")
                        sendMessage.forEachIndexed { index, content ->
                            Timber.tag("ChatContentService").d("$index Chat : $content")
                        }

                        postChatListCompletionsUseCase(
                            sendSystemMessage = getChatRoomSystemRoleUseCase(chatRoomSrl = chatContentInfo.chatRoomSrl).first().map { it.roleContent },
                            sendUserMessage = sendMessage
                        ).filterNotNull()
                        .collect { result ->
                            result.onSuccess {
                                val responseMessage = it.data[0].message.content
                                val responseToken = it.tokenUsage.completionTokens

                                updateChatContentStatusUseCase(
                                    chatContentSrl = chatContentInfo.chatContentSrl,
                                    status = "complete",
                                    updateDate = DateUtil.getFullDate()
                                )

                                insertChatContentUseCase(
                                    chatRoomSrl = chatContentInfo.chatRoomSrl,
                                    parentChatContentSrl = chatContentInfo.chatContentSrl,
                                    role = "assistant",
                                    content = responseMessage,
                                    useToken = responseToken,
                                    status = "complete",
                                    updateDate = DateUtil.getFullDate(),
                                    createDate = DateUtil.getFullDate()
                                )
                            }
                            result.onError { _, _ ->
                                updateChatContentStatusUseCase(
                                    chatContentSrl = chatContentInfo.chatContentSrl,
                                    status = "error",
                                    updateDate = DateUtil.getFullDate()
                                )
                            }
                            result.onException {
                                updateChatContentStatusUseCase(
                                    chatContentSrl = chatContentInfo.chatContentSrl,
                                    status = "error",
                                    updateDate = DateUtil.getFullDate()
                                )
                            }
                        }
                    }
                }
        }
    }
}