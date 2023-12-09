package live.lafi.presentation.chat_room

import android.animation.Animator
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.domain.model.chat.ChatContentInfo
import live.lafi.presentation.R
import live.lafi.presentation.databinding.ActivityChatRoomBinding
import live.lafi.util.base.BaseActivity
import live.lafi.util.public_model.ContentManager
import live.lafi.util.service.ChatContentService
import timber.log.Timber

@AndroidEntryPoint
class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {
    companion object {
        const val CHAT_ROOM_SRL = "chat_room_srl"
        const val CHAT_ROOM_TITLE = "chat_room_title"
    }

    private val viewModel: ChatRoomViewModel by viewModels()

    private val chatContentAdapter by lazy { ChatContentListAdapter() }

    private val chatRoomSrl by lazy { intent.getLongExtra(CHAT_ROOM_SRL, 0L) }
    private val chatRoomTitle by lazy { intent.getStringExtra(CHAT_ROOM_TITLE) ?: "" }

    private var isLottieAnimatorRunning = false

    override fun setupUi() {
        if (chatRoomSrl == 0L) {
            showToast("잘못 된 접근입니다.")
            finish()
        }

        with(binding) {
            tvChatRoomTitle.text = chatRoomTitle

            rvChatContent.apply {
                adapter = chatContentAdapter
                layoutManager = LinearLayoutManager(this@ChatRoomActivity).apply {
                    orientation = LinearLayoutManager.VERTICAL
                    reverseLayout = false
                    stackFromEnd = true
                    setHasFixedSize(true)
                }
                itemAnimator = null
            }

            etTextMessage.requestFocus()
        }
    }

    override fun subscribeUi() {
        with(viewModel) {
            scopeIO.launch {
                getAllChatContentWithChatRoomSrl(chatRoomSrl = chatRoomSrl).collectLatest { chatContentList ->
                    val mappedPairs = mutableListOf<ChatContentInfo>()
                    chatContentList.forEach { chatContent ->
                        if (chatContent.parentChatContentSrl == 0L) { // 질문인 경우
                            chatContentList.find { it.parentChatContentSrl == chatContent.chatContentSrl }?.let {
                                mappedPairs.add(chatContent) // 질문 추가
                                mappedPairs.add(it) // 해당 답변 추가
                            } ?: run {
                                mappedPairs.add(chatContent) // 질문 추가
                                if (chatContent.status == "wait" || chatContent.status == "request") {
                                    mappedPairs.add(
                                        ChatContentInfo(
                                            chatContentSrl = 0L,
                                            chatRoomSrl = chatContent.chatRoomSrl,
                                            parentChatContentSrl = chatContent.chatContentSrl,
                                            role = "assistant",
                                            content = "",
                                            contentSummary = "",
                                            contentTranslate = "",
                                            useToken = 0,
                                            status = "loading",
                                            updateDate = chatContent.updateDate,
                                            createDate = chatContent.createDate
                                        )
                                    ) // 로딩중.. 추가
                                }
                            }
                        }
                    }

                    val chatContentItemList = mappedPairs.map { chatContent ->
                        val viewType = if (chatContent.status == "loading") {
                            ChatContentItem.ViewType.CHAT_CONTENT_OTHER_LOADING
                        } else if (chatContent.role == "user") {
                            ChatContentItem.ViewType.CHAT_CONTENT_MY_TEXT
                        } else {
                            ChatContentItem.ViewType.CHAT_CONTENT_OTHER_TEXT
                        }

                        ChatContentItem(
                            viewType = viewType,
                            chatContentSrl = chatContent.chatContentSrl,
                            content = chatContent.content,
                            profileUri = "",
                            nickname = chatRoomTitle,
                            createDate = chatContent.createDate
                        )
                    }

                    withContext(Dispatchers.Main) {
                        chatContentAdapter.submitList(chatContentItemList) {
                            if (binding.rvChatContent.adapter != null && binding.rvChatContent.adapter!!.itemCount > 0) {
                                binding.rvChatContent.post {
                                    binding.rvChatContent.scrollToPosition(
                                        binding.rvChatContent.adapter!!.itemCount - 1
                                    )
                                }
                            }
                        }
                        //binding.rvChatContent.scrollToPosition(chatContentItemList.size - 1)
                    }
                }
        }   }
    }

    override fun initListener() {
        with(binding) {
            flBackButton.setOnClickListener { finish() }
            flSendButton.setOnClickListener { sendMessage(etTextMessage.text.toString()) }

            etTextMessage.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrBlank()) {
                        flSendButton.visibility = View.GONE
                    } else {
                        flSendButton.visibility = View.VISIBLE
                    }
                }
            })

            ltLogo.addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    // 애니메이션 시작 될 때
                    isLottieAnimatorRunning = true
                }

                override fun onAnimationEnd(animation: Animator) {
                    // 애니메이션 종료 될 때
                    isLottieAnimatorRunning = false
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) { }
            })
        }
    }

    override fun initData() {
    }

    private fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            viewModel.sendChatContent(
                chatRoomSrl = chatRoomSrl,
                content = message
            )
            binding.etTextMessage.text.clear()

            if (!isLottieAnimatorRunning) {
                binding.ltLogo.playAnimation()
            }

            if (!ContentManager.isContentServiceRunning()) {
                startService(
                    Intent(applicationContext, ChatContentService::class.java)
                )
            }
        }
    }
}