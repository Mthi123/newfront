package com.example.projeeeeeeeeeect

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

// ViewBinding
import com.example.projeeeeeeeeeect.databinding.ActivityConversationBinding

// ViewModels
import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView

class ConversationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1️⃣ Get the channel CID from backend
        val cid = intent.getStringExtra("channelId") ?: run {
            finish()
            return
        }

        // 2️⃣ Create unified factory
        val factory = MessageListViewModelFactory(this, cid)

        // 3️⃣ Get ViewModels
        val provider = ViewModelProvider(this, factory)
        val headerViewModel: MessageListHeaderViewModel = provider[MessageListHeaderViewModel::class.java]
        val listViewModel: MessageListViewModel = provider[MessageListViewModel::class.java]
        val composerViewModel: MessageComposerViewModel = provider[MessageComposerViewModel::class.java]

        // 4️⃣ Bind views
        headerViewModel.bindView(binding.messageListHeaderView, this)
        listViewModel.bindView(binding.messageListView, this)
        composerViewModel.bindView(binding.messageComposerView, this)

        // 5️⃣ Set back button
        binding.messageListHeaderView.setBackButtonClickListener { finish() }
    }
}