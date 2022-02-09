package com.example.myapplication.repo

import android.util.Log
import com.example.myapplication.model.FarmIssue
import com.example.myapplication.network.HttpClient
import com.example.myapplication.network.MainService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserRemoteRepository: UserRepository {

    override fun postTotal(farmIssue: FarmIssue): Single<Boolean> {
        return HttpClient.client
            .create(MainService::class.java)
            .postTotal(farmIssue)
            .subscribeOn(Schedulers.io())
            .map {
                it.success
            }
            .onErrorReturn { error ->
                Log.d("error", error.toString())
                false
            }
    }//API(POST) 통신 구현

    companion object {
        fun getInstance(): UserRemoteRepository {
            return LazyHolder.INSTANCE
        }
    }
    private object LazyHolder {
        val INSTANCE = UserRemoteRepository()
    }

}
