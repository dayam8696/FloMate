package com.example.flomate.ui.fragments.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import com.example.flomate.databinding.ChatBotFragmentBinding
import com.example.flomate.ui.fragments.BaseFragment

class ChatBotFragment : BaseFragment() {
    private val binding by lazy { ChatBotFragmentBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.startchat.setOnClickListener {
            openUrl("https://ameenurrehman.github.io/Valentina-/")
        }
    }

    private fun openUrl(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}