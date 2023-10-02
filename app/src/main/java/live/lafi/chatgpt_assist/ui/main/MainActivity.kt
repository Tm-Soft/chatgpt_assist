package live.lafi.chatgpt_assist.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.chatgpt_assist.R
import live.lafi.chatgpt_assist.base.BaseActivity
import live.lafi.chatgpt_assist.databinding.ActivityMainBinding
import live.lafi.data.DataExample
import live.lafi.domain.DomainExample
import live.lafi.util.GetGptToken
import live.lafi.util.model.GptChatMessage
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        subscribeUi()
        //initAdMob()

        lifecycleScope.launch(Dispatchers.Default) {
            Timber.tag("test").d("시작합니다.")
            val timeTick = System.currentTimeMillis()
            val token = GetGptToken(
                GetGptToken.GptModelType.GPT_3_5_TURBO,
                listOf(
                    GptChatMessage(
                        "user",
                        "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                    ),
                    GptChatMessage(
                        "user",
                        "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                    ),
                    GptChatMessage(
                        "user",
                        "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                    ),
                    GptChatMessage(
                        "user",
                        "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                    )
                )
            )
            Timber.tag("test").d("끝")
            withContext(Dispatchers.Main) {
                //showToast("토큰 : $token // ${(System.currentTimeMillis() - timeTick)/1000}초 걸림")
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            tvTextFirst.text = DomainExample().getValue()
            tvTextSecond.text = DataExample().getValue()
        }
    }

    private fun subscribeUi() {
        with(mainViewModel) {
            testValue.observe(this@MainActivity) {
                showToast(it)
            }
        }
    }

    private fun initAdMob() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object: InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    showToast("광고 불러오기 실패$p0")
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    showToast("광고 불러오기 성공")
                    interstitialAd.show(this@MainActivity)
                }
            }
        )
    }
}