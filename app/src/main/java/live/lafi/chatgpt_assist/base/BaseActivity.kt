package live.lafi.chatgpt_assist.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import timber.log.Timber

abstract class BaseActivity<T: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {
    lateinit var binding: T
    abstract val TAG: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)

        Timber.tag(TAG).i("onCreate")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).i("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(TAG).i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(TAG).i("onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(TAG).i("onDestroy")
    }

    fun showToast(
        msg: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(this, msg, duration).show()
    }
}