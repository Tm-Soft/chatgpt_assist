package live.lafi.util.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel: ViewModel() {
    private val viewModelJob = SupervisorJob()

    // private val singleThreadDispatcher = newSingleThreadContext("singleThreadDispatcher")

    val scopeMain = CoroutineScope(Dispatchers.Main + viewModelJob)
    val scopeIO = CoroutineScope(Dispatchers.IO + viewModelJob)
    val scopeDefault = CoroutineScope(Dispatchers.Default + viewModelJob)

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}