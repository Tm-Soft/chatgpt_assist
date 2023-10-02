package live.lafi.chatgpt_assist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import live.lafi.chatgpt_assist.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel() {
    private val _testValue = MutableLiveData<String>("안녕")
    val testValue: LiveData<String> get() = _testValue
}