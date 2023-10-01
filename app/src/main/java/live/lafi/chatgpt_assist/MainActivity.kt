package live.lafi.chatgpt_assist

import android.os.Bundle
import android.widget.Toast
import live.lafi.chatgpt_assist.base.BaseActivity
import live.lafi.chatgpt_assist.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvText.text = "안녕 변경을 진행 할께요."
        showToast("토스트 메시지 입니다.")
    }
}