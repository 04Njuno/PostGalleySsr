package com.example.myapplication.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.FarmIssue
import com.example.myapplication.repo.UserRemoteRepository
import com.example.myapplication.util.RxAction
import com.example.myapplication.util.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable


class MainViewModel: ViewModel() {
    val editNum = ObservableField("")
    val editAddress = ObservableField("")

    private val userRepository = UserRemoteRepository.getInstance()
    private val disposables = CompositeDisposable()

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    fun postTotal() {
        userRepository.postTotal(FarmIssue(editNum = editNum.get().toString() ?: "", editAddress = editAddress.get() ?:"" , imgUrl = ""))
            .subscribe { result ->
                itemEventRelay.accept(AddSuccessEvent(result))
            }
            .addTo(disposables)
    }//데이터 전송 
    class AddSuccessEvent(val isSuccess: Boolean) : RxAction
    //성공 시 보내지는 값
}
