package client.view.fragment.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.presenter.chat.ChatPresenter;
import client.presenter.chat.IChatPresenter;
import client.view.activity.GameActivity;
import client.view.fragment.SidebarFragment;
import shared.enumeration.PlayerColor;
import shared.model.Message;

public class ChatFragment extends SidebarFragment implements IGameChatView {

    private static final int MAX_CHARACTERS_IN_MESSAGE = 160;

    private List<Message> _messages;
    private IChatPresenter _presenter;
    private EditText _messageInputBox;
    private Button _sendMessageButton;
    private ChatListAdapter _chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_chat_list, container, false);

        if (getActivity() instanceof GameActivity) {
            setupSidebarButtons(ButtonType.CHAT);
        }

        // Initialize Simple Members
        _messages = new ArrayList<>();
        _chatAdapter = new ChatListAdapter(_messages);
        _presenter = new ChatPresenter(this);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(MAX_CHARACTERS_IN_MESSAGE);

        // Initialize View Members
        RecyclerView _chatRecyclerView = v.findViewById(R.id.chat_list_recycler_view);
        _messageInputBox = v.findViewById(R.id.chat_list_input_message);
        _sendMessageButton = v.findViewById(R.id.chat_list_send_chat_button);

        // Modify View Members
        _sendMessageButton.setEnabled(false);
        _messageInputBox.setFilters(filterArray);
        _chatRecyclerView.setAdapter(_chatAdapter);
        _chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((LinearLayoutManager)_chatRecyclerView.getLayoutManager()).setReverseLayout(true);

        // Set View OnClickListeners
        _sendMessageButton.setOnClickListener((view) -> {
            _presenter.sendMessage(_messageInputBox.getText().toString());
            _messageInputBox.clearFocus();
        });

        _messageInputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    _sendMessageButton.setEnabled(true);
                } else {
                    _sendMessageButton.setEnabled(false);
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void clearInput(){
        _messageInputBox.setText("");
    }

    @Override
    public void enableInput() {
        _messageInputBox.setEnabled(true);
    }

    @Override
    public void disableInput() {
        _messageInputBox.setEnabled(false);
    }

    @Override
    public void enableSendButton() {
        _sendMessageButton.setEnabled(true);
    }

    @Override
    public void disableSendButton() {
        _sendMessageButton.setEnabled(false);
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean addMessage(Message message) {
        if  (_messages.contains(message)) return false;

        _messages.add(message);
        Collections.sort(_messages, Message.getDescendingComparator());

        getActivity().runOnUiThread(() ->{
            _chatAdapter.notifyDataSetChanged();
        });

        return true;
    }

    private class ChatItemHolder extends RecyclerView.ViewHolder{

        private TextView _content;
        private TextView _timestamp;
        private RelativeLayout _container;

        ChatItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.chat_list_item, parent, false));

            _content = itemView.findViewById(R.id.chat_list_item_message);
            _timestamp = itemView.findViewById(R.id.chat_list_item_timestamp);
            _container = itemView.findViewById(R.id.chat_list_message_parent);
        }

        public void bind(Message message){
            _content.setText(message.getMessage());
            _timestamp.setText(message.getTimeStamp().toString());


            LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)_container.getLayoutParams();

            // TODO: set isOwner based on if the ID matches the id in the model
            boolean isOwner = _presenter.getClientDisplayName().equals(message.getDisplayName());
            PlayerColor color = message.getPlayer().getColor();

            if (isOwner){
                layout.setMargins(20, 0, 0,0);
            } else {
                layout.setMargins(0, 0, 20,0);
                _container.setGravity(Gravity.START);
            }

            switch (color){
                case BLACK:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_black));
                    break;

                case BLUE:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_blue));
                    break;

                case GREEN:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_green));
                    break;

                case RED:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_red));
                    break;

                case YELLOW:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_yellow));
                    break;
            }

        }
    }

    private class ChatListAdapter extends RecyclerView.Adapter<ChatItemHolder> {

        private List<Message> _messages;

        ChatListAdapter(List<Message> messages) {
            _messages = messages;
        }

        @NonNull
        @Override
        public ChatItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ChatItemHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatItemHolder holder, int position) {
            Message message = _messages.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            return _messages.size();
        }
    }
}
