package com.example.flomate.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flomate.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}