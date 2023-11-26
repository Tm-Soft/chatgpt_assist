package live.lafi.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

abstract class BaseBottomSheetFragment<T: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : BottomSheetDialogFragment() {
    lateinit var binding: T
    private val TAG by lazy { this.javaClass.simpleName }

    override fun onAttach(context: Context) {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("LifeCycleTracker-F").d("$TAG - onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onCreateView")

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onViewCreated")

        binding.lifecycleOwner = this@BaseBottomSheetFragment

        super.onViewCreated(view, savedInstanceState)

        setupUi()
        subscribeUi()
        initListener()
        initData()
    }

    override fun onStart() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onStart")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onStop")
        super.onStop()
    }

    override fun onDetach() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onDetach")
        super.onDetach()
    }

    override fun onDestroyView() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag("LifeCycleTracker-F").d("$TAG - onDestroy")
        super.onDestroy()
    }

    abstract fun setupUi()

    abstract fun subscribeUi()

    abstract fun initListener()

    abstract fun initData()
}