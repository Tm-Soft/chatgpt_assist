package live.lafi.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import live.lafi.domain.ApiResult
import retrofit2.HttpException
import java.io.IOException

fun <T : Any> handleFlowApi(
    execute: suspend () -> T,
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.LoadingStart) //값 갱신전 로딩을 emit
    try {
        emit(ApiResult.Success(execute())) // execute 성공시 해당값을 Success에 담아서 반환
        emit(ApiResult.LoadingEnd)
    } catch (e: HttpException) {
        emit(ApiResult.Fail.Error(code = e.code(), message = e.message())) // 네트워크 오류시 code와 메세지를 반환
        emit(ApiResult.LoadingEnd)
    } catch (e: IOException) {
        emit(ApiResult.Fail.Exception(e = e)) // 네트워크 오류시 code와 메세지를 반환
        emit(ApiResult.LoadingEnd)
    } catch (e: IllegalArgumentException) {
        emit(ApiResult.Fail.Exception(e = e)) // 네트워크 오류시 code와 메세지를 반환
        emit(ApiResult.LoadingEnd)
    } catch (e: Exception) {
        emit(ApiResult.Fail.Exception(e = e)) // 예외 발생시 해당 에러를 반환
        emit(ApiResult.LoadingEnd)
    }
}