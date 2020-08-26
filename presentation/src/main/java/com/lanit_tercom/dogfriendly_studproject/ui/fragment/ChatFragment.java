package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.MessageEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.data.repository.MessageRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChatPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ChatActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.MessageAdapter;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.EditChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.EditChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.DeleteMessageUseCase;
import com.lanit_tercom.domain.interactor.message.EditMessageUseCase;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.PostMessageUseCase;
import com.lanit_tercom.domain.interactor.message.impl.DeleteMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.EditMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.GetMessagesUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.PostMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl;
import com.lanit_tercom.domain.repository.ChannelRepository;
import com.lanit_tercom.domain.repository.MessageRepository;
import com.lanit_tercom.domain.repository.UserRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

/**
 *  Фрагмент, отвечающий за отображение диалога между двумя пользователями
 *  @author dshebut@rambler.ru
 */
public class ChatFragment extends BaseFragment implements ChatView {

    private ChatPresenter chatPresenter;

    private RecyclerView chat;
    private MessageAdapter messageAdapter;

    private ImageView emptyChatBackground;
    private TextView emptyChatHint;

    private TextView channelName;
    private TextView additionalInfo;
    private ImageView channelAvatar;

    private String channelID;
    private ChannelModel channelModel;

    public ChatFragment(ChannelModel channelModel){

        this.channelModel = channelModel;
        this.channelID = channelModel.getId();
    }

    //region Implementations
    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        MessageEntityDtoMapper messageEntityDtoMapper = new MessageEntityDtoMapper();
        MessageCache messageCache = null;
        MessageEntityStoreFactory messageEntityStoreFactory =
                new MessageEntityStoreFactory(networkManager, messageCache);
        MessageRepository messageRepository = MessageRepositoryImpl
                .getInstance(messageEntityStoreFactory, messageEntityDtoMapper);

        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();
        UserCache userCache = null;
        UserEntityStoreFactory userEntityStoreFactory =
                new UserEntityStoreFactory(networkManager, userCache);
        UserRepository userRepository = UserRepositoryImpl
                .getInstance(userEntityStoreFactory, userEntityDtoMapper);

        ChannelEntityDtoMapper channelEntityDtoMapper = new ChannelEntityDtoMapper();
        ChannelCache channelCache = null;
        ChannelEntityStoreFactory channelEntityStoreFactory =
                new ChannelEntityStoreFactory(networkManager, channelCache);
        ChannelRepository channelRepository = ChannelRepositoryImpl
                .getInstance(channelEntityStoreFactory, channelEntityDtoMapper);


        DeleteMessageUseCase deleteMessageUseCase =
                new DeleteMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        EditMessageUseCase editMessageUseCase =
                new EditMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        GetMessagesUseCase getMessagesUseCase =
                new GetMessagesUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        PostMessageUseCase postMessageUseCase =
                new PostMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);

        GetUserDetailsUseCase getUserDetailsUseCase = new GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread);
        EditChannelUseCase editChannelUseCase = new EditChannelUseCaseImpl(channelRepository,
                threadExecutor, postExecutionThread);

        chatPresenter = new ChatPresenter(channelModel, authManager,
                deleteMessageUseCase, editMessageUseCase,
                getMessagesUseCase, postMessageUseCase,
                getUserDetailsUseCase, editChannelUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        showLoading();
        initEmptyChat(root);
        initRecyclerView(root);
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatPresenter.setView(this);
        chatPresenter.refreshData();
        renderMessages();
    }

    @Override
    public void showProgressMessage(@NotNull String event) {
        //Пока отобразим только тост
        Toast.makeText(getContext(), event, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {
        //TODO отображение загрузки в UI
    }

    @Override
    public void hideLoading() {
        //TODO скрытие загрузки в UI
    }

    @Override
    public void showError(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void renderMessages() {
        if (chatPresenter.isChannelEmpty()){
            chat.setVisibility(View.INVISIBLE);
            emptyChatBackground.setVisibility(View.VISIBLE);
            emptyChatHint.setVisibility(View.VISIBLE);
        }else{
            chat.setVisibility(View.VISIBLE);
            emptyChatBackground.setVisibility(View.INVISIBLE);
            emptyChatHint.setVisibility(View.INVISIBLE);

            messageAdapter.setMessages(
                    chatPresenter.getMessages());
            chat.smoothScrollToPosition(
                    messageAdapter.getItemCount());
        }
    }

    @Override
    public void showMessageMenu(@NotNull MessageModel message, int position,
                                @NotNull View targetView) {
        PopupMenu messageMenu = new PopupMenu(targetView.getContext(), targetView);
        messageMenu.inflate(R.menu.item_message_menu);
        boolean canBeEdited = message.getSenderID()
                .equals(chatPresenter.getCurrentUserID())
                && position == messageAdapter.getItemCount() -1; // Оставляем редактирование
        // только последнего сообщения?
        messageMenu.getMenu().findItem(R.id.item_edit_message)
                .setVisible(canBeEdited);
        messageMenu.setOnMenuItemClickListener(menuItem ->{
            switch (menuItem.getItemId()){
                case R.id.item_edit_message:
                    showEditDialog(message);
                    return true;
                case R.id.item_delete_message:
                    chatPresenter.deleteMessage(message);
                    return true;
                default:
                    return false;
            }
        });
        messageMenu.show();
    }
    //endregion

    //region Initialisations
    private void initInteractions(@NotNull View root){
        //TODO смена иконки кнопки "отправить сообщение" при вызове
        // программной клавиатуры, согласно макету
        channelName = root.findViewById(R.id.chat_screen_user_name);
        additionalInfo = root.findViewById(R.id.chat_screen_user_additional_info);
        channelAvatar = root.findViewById(R.id.chat_screen_user_avatar);

        EditText messageText = root.findViewById(R.id.edit_text_send_message);
        ImageButton sendMessage = root.findViewById(R.id.button_send_message);
        sendMessage.setOnClickListener(v -> sendMessage(messageText));

        ImageButton backToDialogs = root.findViewById(R.id.button_back);
        backToDialogs.setOnClickListener(v -> backToDialogsFragment());

        ImageButton chatMenu = root.findViewById(R.id.button_chat_menu);
        ImageView chatMenuBlurEffect = root.findViewById(R.id.on_chat_menu_blur_effect);
        chatMenuBlurEffect.setVisibility(View.GONE);

        ConstraintLayout upperSpace = root.findViewById(R.id.upper_space); //Используем для корректного вызова
        // и отображения PopupMenu
        chatMenu.setOnClickListener(v -> showChatMenu(root, upperSpace));
    }



    private void initRecyclerView(@NotNull View root){
        chat = root.findViewById(R.id.chat);
        chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessageAdapter(this, chatPresenter.getCurrentUserID());
        chat.setAdapter(messageAdapter);
    }


    private void initEmptyChat(View root){
        emptyChatBackground = root.findViewById(R.id.empty_chat_background);
        emptyChatHint = root.findViewById(R.id.empty_chat_hint);
    }

    //endregion

    //region Actions
    private void sendMessage(@NotNull EditText messageText){
        chatPresenter.sendMessage(messageText
                .getText().toString());
    }

    private void backToDialogsFragment(){
        ChatActivity baseActivity = (ChatActivity) getActivity();
        baseActivity.navigateToChannelsList();
    }

    private void showEditDialog(MessageModel message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        final EditText editText = new EditText(getContext());
        editText.setText(message.getText());
        dialog.setView(editText);
        dialog.setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
            message.setText(editText.getText().toString());
            chatPresenter.editMessage(message);
        });
        dialog.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
        });
        dialog.show();
    }

    //TODO кастомизаця PopupMenu
    private void showChatMenu(View root, ConstraintLayout upperSpace){
        ChatActivity baseActivity = (ChatActivity) getActivity();
        ImageView blur = root.findViewById(R.id.on_chat_menu_blur_effect);
        blur.setVisibility(View.VISIBLE);
        PopupMenu chatMenu = new PopupMenu(upperSpace.getContext(), upperSpace);
        chatMenu.inflate(R.menu.chat_menu);
        chatMenu.setOnDismissListener(listener -> blur.setVisibility(View.GONE));
        chatMenu.setOnMenuItemClickListener(menuItem ->{
            switch (menuItem.getItemId()){
                case R.id.item_open_user_profile:
                    //TODO переход к профилю пользователя
                    baseActivity.navigateToUserDetailObserver(chatPresenter.getCurrentUserID(),
                            chatPresenter.getAddresseeID());
                    return true;
                case R.id.item_disable_notification:
                    return true;
                case R.id.item_add_user:
                    return true;
                case R.id.item_delete_all_messages:
                    //TODO очистить переписку
                    return true;
                default:
                    return false;
            }
        });
        chatMenu.show();
    }

    @Override
    public void updateChannelInfo(@NotNull String name, @NotNull String status, String avatar) {
        channelName.setText(name);
        additionalInfo.setText(status);
        Glide.with(this)
                .load(avatar)
                .circleCrop()
                .into(channelAvatar);
    }

    //endregion
}
