package com.marknjunge.core.data.network.interceptors

import com.marknjunge.core.model.ApiResponse
import com.marknjunge.core.utils.appConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber

class ConvertNoContentInterceptor : Interceptor {
    private val mediaType = "application/json".toMediaType()

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().headers.forEach {

            Timber.d("${it.first}: ${it.second}")


        }
        val response = chain.proceed(chain.request())

        return if (response.code == 204 || response.body?.contentLength() == 0L) {

            val apiResponse = ApiResponse("No content")
            val rawBody = JsonConfiguration.appConfig.stringify(ApiResponse.serializer(), apiResponse)
            Timber.d(rawBody)

            Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .body(rawBody.toResponseBody(mediaType))
                .request(chain.request())
                .message("")
                .build()
        } else {
            response
        }
    }
}