package com.example.flomate.ui.fragments.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flomate.databinding.ChatBotFragmentBinding
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.utils.Constants.CHAT_BOT

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
        navigationVisibility(true)
        binding.startchat.setOnClickListener {
            openUrl()
        }
    }

    private fun openUrl() {
        val uri = Uri.parse(CHAT_BOT)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}