package live.lafi.util.base

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
    private val TAG by lazy { this.javaClass.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)

        setupUi()
        subscribeUi()
        initListener()
        initData()

        Timber.tag("LifeCycleTracker-A").d("$TAG - onCreate")
    }

    override fun onResume() {
        Timber.tag("LifeCycleTracker-A").d("$TAG - onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag("LifeCycleTracker-A").d("$TAG - onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag("LifeCycleTracker-A").d("$TAG - onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.tag("LifeCycleTracker-A").d("$TAG - onDestroy")
        super.onDestroy()
    }

    abstract fun setupUi()
    abstract fun subscribeUi()
    abstract fun initListener()
    abstract fun initData()

    fun showToast(
        msg: String,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(this, msg, duration).show()
    }
}