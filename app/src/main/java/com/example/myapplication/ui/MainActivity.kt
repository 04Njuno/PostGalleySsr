package com.example.myapplication.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.util.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        requestPermission()
        observeEvent()

        binding.btnForImg.setOnClickListener(){
            openSomeActivityForResult()
        }
    }
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                binding.image.setImageURI(uri)
            }
        }
    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
    private fun requestPermission() {
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(this, "권한 확인 $it", Toast.LENGTH_SHORT).show()
        }
        permissionLauncher.launch(android.Manifest.permission.INTERNET)
        permissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    //rx
    private fun observeEvent() {
        viewModel.itemEventRelay
            .ofType(MainViewModel.AddSuccessEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, it.isSuccess.toString(), Toast.LENGTH_SHORT).show()
            }
            .addTo(disposable)
    }
    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}