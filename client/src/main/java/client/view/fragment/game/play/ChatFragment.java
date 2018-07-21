package client.view.fragment.game.play;

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

import client.presenter.game.play.ChatPresenter;
import client.presenter.game.play.IChatPresenter;
import client.view.fragment.SidebarFragment;
import shared.enumeration.PlayerColor;
import shared.model.Message;

public class ChatFragment extends SidebarFragment implements IGameChatView {

    private static final int MAX_CHARACTERS_IN_MESSAGE = 160;

    private IChatPresenter _presenter;

    private EditText _messageInputBox;
    private Button _sendMessageButton;
    private RecyclerView _chatRecyclerView;

    private ChatListAdapter _chatAdapter;

    List<Message> _messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_chat_list, container, false);

        setupSidebarButtons(ButtonType.CHAT);

        _messages = new ArrayList<>();
        _chatAdapter = new ChatListAdapter(_messages);
        _chatRecyclerView = v.findViewById(R.id.chat_list_recycler_view);
        _chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((LinearLayoutManager)_chatRecyclerView.getLayoutManager()).setReverseLayout(true);
        _chatRecyclerView.setAdapter(_chatAdapter);

        // Views should not create presenters...
        _presenter = new ChatPresenter(this);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(MAX_CHARACTERS_IN_MESSAGE);

        _messageInputBox = v.findViewById(R.id.chat_list_input_message);
        _messageInputBox.setFilters(filterArray);
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

        _sendMessageButton = v.findViewById(R.id.chat_list_send_chat_button);
        _sendMessageButton.setEnabled(false);
        _sendMessageButton.setOnClickListener((view) -> {
            _presenter.sendMessage(_messageInputBox.getText().toString());
            _messageInputBox.clearFocus();
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        _presenter.destroy();
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMessages(List<Message> messages){
        _messages = new ArrayList<>(messages);
        Collections.sort(_messages, Message.getDescendingComparator());
        _chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void addMessage(Message message) {
        _messages.add(message);
        Collections.sort(_messages, Message.getDescendingComparator());
        _chatAdapter.notifyDataSetChanged();
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

        //private List<Message> _messages;

        ChatListAdapter(List<Message> messages) {
            //_messages = messages;
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
