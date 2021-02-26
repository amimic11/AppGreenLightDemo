package com.example.appgreenlightdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.appgreenlightdemo.R
import com.example.appgreenlightdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binder.root)
    }
}