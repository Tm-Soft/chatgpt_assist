package live.lafi.chatgpt_assist.ui.main

import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import live.lafi.chatgpt_assist.R
import live.lafi.chatgpt_assist.base.BaseActivity
import live.lafi.chatgpt_assist.databinding.ActivityMainBinding
import live.lafi.data.DataExample
import live.lafi.domain.DomainExample
import live.lafi.util.GetGptToken
import live.lafi.util.model.GptChatMessage

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        //initAdMob()

        val token = GetGptToken(
            GetGptToken.GptModelType.GPT_3_5_TURBO,
            listOf(
                GptChatMessage(
                    "user",
                    "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                )
            )
        )

        showToast(
            "토큰 : " + token
        )
    }

    private fun setupUi() {
        with(binding) {
            tvTextFirst.text = DomainExample().getValue()
            tvTextSecond.text = DataExample().getValue()
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
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    showToast("광고 불러오기 성공")
                    mInterstitialAd?.show(this@MainActivity)
                }
            }
        )
    }
}