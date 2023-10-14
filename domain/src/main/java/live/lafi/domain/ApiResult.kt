package live.lafi.domain

sealed class ApiResult<out T> {
    object LoadingStart: ApiResult<Nothing>()
    object LoadingEnd: ApiResult<Nothing>()

    data class Success<out T>(val data: T): ApiResult<T>()

    sealed class Fail: ApiResult<Nothing>() {
        data class Error(val code: Int, val message: String?): Fail()
        data class Exception(val e:Throwable): Fail()
    }

    inline fun <reified T : Any> ApiResult<T>.onLoadingStart(action: () -> Unit) {
        if (this is ApiResult.LoadingStart) action()
    }

    inline fun <reified T : Any> ApiResult<T>.onLoadingEnd(action: () -> Unit) {
        if (this is ApiResult.LoadingEnd) action()
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