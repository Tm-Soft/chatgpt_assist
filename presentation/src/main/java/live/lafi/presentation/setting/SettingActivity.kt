package live.lafi.presentation.setting

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.expandablelayout.ExpandableAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.presentation.base.BaseActivity
import live.lafi.presentation.databinding.ActivitySettingBinding
import timber.log.Timber
import kotlin.math.exp

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel: SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        subscribeUi()
        initListener()
        initData()
    }

    private fun setupUi() {}

    private fun subscribeUi() {
        lifecycleScope.launch {
            viewModel.getMaxUseToken().collectLatest { setMaxUseToken ->
                binding.seekBarMaxToken.progress = setMaxUseToken
                binding.tvMaxTokenText.text = "$setMaxUseToken Token"
            }
        }

        lifecycleScope.launch {
            viewModel.getChatGptToken().collectLatest { setToken ->
                if (setToken.isNotEmpty()) {
                    val viewToken = if (setToken.length > 10) {
                        setToken.substring(0, 20) + "..."
                    } else {
                        setToken
                    }
                    binding.tvTokenAlready.text = viewToken
                } else {
                    binding.tvTokenAlready.text = "Open AI - Api Key 가 존재하지 않습니다"
                }
            }
        }

        with(viewModel) {
            onLoading.observe(this@SettingActivity) { isLoading ->
                binding.btnTokenModify.isEnabled = !isLoading
            }
        }
    }

    private fun initListener() {
        with(binding) {
            flBackButton.setOnClickListener { onBackPressed() }

            btnTokenModify.setOnClickListener { showEditGptToken() }

            seekBarMaxToken.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) { tvMaxTokenText.text = "$progress Token" }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let { viewModel.updateMaxUseToken(it.progress) }
                }
            })

            // 내부 this는 expTokenInfo 전체 사용.
            with(expTokenInfo) {
                parentLayout.setOnClickListener {
                    if (isExpanded) collapse()
                    else expand()
                }

                secondLayout.setOnClickListener { collapse() }
            }
        }
    }

    private fun initData() {}

    private fun showEditGptToken() {
        Dialog.with(this@SettingActivity)
            .title("ChatGpt API Token 입력")
            .content("https://platform.openai.com/account/api-keys 에서 발급 받은 API Key 입력 해주세요")
            .editTextHint("sk-xxxxxxxxxxxxxxxxxxxxxxxxxxx")
            .positiveText("변경")
            .negativeText("닫기")
            .setEditTextMaxLine(1)
            .setEditTextImeOption(EditorInfo.IME_ACTION_DONE)
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {
                    viewModel.updateChatGptToken(
                        token = inputText,
                        success = { showToast("ChatGPT Token 변경 완료") },
                        fail = { showToast("유효 하지 않은 Token") }
                    )
                } else {
                    showToast("API Key를 입력 해주세요")
                }
            }
            .showEditTextDialog()
    }
}