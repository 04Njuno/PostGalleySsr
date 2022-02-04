package com.example.myapplication.network

import com.example.myapplication.model.FarmIssue
import com.example.myapplication.network.response.DefaultResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {

    @POST("farmIssue")
    fun postTotal(
        @Body farmIssue: FarmIssue
    ): Single<DefaultResponse>

}