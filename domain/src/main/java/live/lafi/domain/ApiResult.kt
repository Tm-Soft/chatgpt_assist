package live.lafi.domain

sealed class ApiResult<out T> {
    object Loading: ApiResult<Nothing>()

    data class Success<out T>(val data: T): ApiResult<T>()

    sealed class Fail: ApiResult<Nothing>() {
        data class Error(val code: Int, val message: String?): Fail()
        data class Exception(val e:Throwable): Fail()
    }

    inline fun <reified T : Any> ApiResult<T>.onLoading(action: () -> Unit) {
        if (this is ApiResult.Loading) action()
    }

    inline fun <reified T : Any> ApiResult<T>.onSuccess(action: (data: T) -> Unit) {
        if (this is ApiResult.Success) action(data)
    }

    inline fun <reified T : Any> ApiResult<T>.onError(action: (code: Int, message: String?) -> Unit) {
        if (this is ApiResult.Fail.Error) action(code, message)
    }

    inline fun <reified T : Any> ApiResult<T>.onException(action: (e: Throwable) -> Unit) {
        if (this is ApiResult.Fail.Exception) action(e)
    }
}