package com.prj.coroutineex.mvvm.retrofit

import android.util.Log
import com.prj.coroutineex.mvvm.data.Output
import retrofit2.Response
import java.io.IOException
// https://jwsoft91.tistory.com/103
// 34202aed91374511a43f567392fd271f
open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call : suspend() -> Response<T>, error:String) : T?{
        val result = newsApiOutput(call, error)
        var output : T? = null

        when(result){
            is Output.Success ->
                output = result.output
            is Output.Error ->
                Log.d("Error", "error : ${result.exception}")
        }

        return output
    }

    private suspend fun <T : Any> newsApiOutput(call: suspend() -> Response<T>, error:String ) : Output<T>{
        val response = call.invoke()
        return if (response.isSuccessful) {
            Log.d("TestApp", "주소 : ${response.raw()}")
            Output.Success(response.body()!!)
        }
        else
            Output.Error(IOException("OOps .. Something went wrong due to $error"))

    }
}