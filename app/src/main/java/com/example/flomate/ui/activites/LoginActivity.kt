package com.example.flomate.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.caringcatalysts.R
import com.example.flomate.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}