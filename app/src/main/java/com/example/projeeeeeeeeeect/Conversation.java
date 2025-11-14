//package com.example.projeeeeeeeeeect;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.projeeeeeeeeeect.databinding.ActivityConversationBinding;
//
//import io.getstream.chat.android.client.ChatClient;
//import io.getstream.chat.android.models.Channel;
//import io.getstream.chat.android.models.Member;
//import io.getstream.chat.android.models.User;
//
//import io.getstream.chat.android.ui.feature.messages.list.MessageListView;
//import io.getstream.chat.android.ui.feature.messages.header.MessageListHeaderView;
//import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerView;
//
//import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel;
//import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel;
//import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel;
//import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory;
//
//public class Conversation extends AppCompatActivity {
//
//    private ActivityConversationBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityConversationBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // --- 1️⃣ Get channel ID from backend/API ---
//        String cid = getIntent().getStringExtra("channelId");
//        if (cid == null || cid.isEmpty()) {
//            Log.e("ConversationActivity", "Channel ID missing, finishing");
//            finish();
//            return;
//        }
//
//        // --- 2️⃣ Create unified factory ---
//        MessageListViewModelFactory factory = new MessageListViewModelFactory(this, cid);
//
//        // --- 3️⃣ Get ViewModels ---
//        ViewModelProvider provider = new ViewModelProvider(this, factory);
//        MessageListHeaderViewModel headerViewModel = provider.get(MessageListHeaderViewModel.class);
//        MessageListViewModel listViewModel = provider.get(MessageListViewModel.class);
//        MessageComposerViewModel composerViewModel = provider.get(MessageComposerViewModel.class);
//
//        // --- HEADER ---
//        headerViewModel.getChannel().observe(this, channel -> {
//            if (channel != null) {
//                binding.messageListHeaderView.setTitle(getChannelName(channel));
//                binding.messageListHeaderView.setAvatar(channel);
//            }
//        });
//
//        // --- MESSAGE LIST ---
//        // MessageListView observes the ViewModel internally, so no manual updates required
//        listViewModel.getState().observe(this, state -> {
//            if (state instanceof MessageListViewModel.State.Result) {
//                // Optional: additional UI handling if needed
//            }
//        });
//
//    }
//
//    // Helper method to generate a channel title
//    private String getChannelName(Channel channel) {
//        User currentUser = ChatClient.instance().getCurrentUser();
//        if (currentUser == null) return "Chat";
//
//        for (Member member : channel.getMembers()) {
//            if (!member.getUserId().equals(currentUser.getId())) {
//                return "Chat with " + member.getUserId();
//            }
//        }
//        return "Chat";
//    }
//}
