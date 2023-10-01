package live.lafi.chatgpt_assist.ui.main

import android.os.Bundle
import live.lafi.chatgpt_assist.R
import live.lafi.chatgpt_assist.base.BaseActivity
import live.lafi.chatgpt_assist.databinding.ActivityMainBinding
import live.lafi.data.DataExample
import live.lafi.domain.DomainExample

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTextFirst.text = DomainExample().getValue()
        binding.tvTextSecond.text = DataExample().getValue()
        showToast("토스트 메시지 입니다.")
    }
}