package com.marknjunge.core.data.network

import com.marknjunge.core.data.model.VerifyOrderDto
import com.marknjunge.core.data.model.VerifyOrderResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface CartService {
    @POST("orders/verify")
    suspend fun verifyCart(@Header("session-id") sessionId: String, @Body body: VerifyOrderDto): List<VerifyOrderResponse>
}