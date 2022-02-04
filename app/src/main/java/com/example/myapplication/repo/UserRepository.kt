package com.example.myapplication.repo

import com.example.myapplication.model.FarmIssue
import io.reactivex.Single

interface UserRepository {
    fun postTotal(farmIssue: FarmIssue): Single<Boolean>
}