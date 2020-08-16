package com.lanit_tercom.dogfriendly_studproject.ui.viewholder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.library.presentation.ui.viewholder.BaseViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *  ViewHolder, необходимый для оптимизации работы со списком
 *  при работе с RecyclerView
 *  @author dshebut@rambler.ru
 */
public class MessageViewHolder extends BaseViewHolder{

    private ConstraintSet constraintSet;

    private final String MESSAGE_TIME_PATTERN = "HH:mm";

    private ChatView parentView; //Передаем сюда ChatView, чтобы иметь
                                //возможность отобразить контекстное меню
                                //с действиями для одного сообщения

    private TextView textView;
    private TextView timeView;
    LinearLayout singleMessageLine; // Эта дополнительная обертка нужна, чтобы распологать
                                    // сообщение справа или слева на экране чата, в зависимости от
                                    // ID отправителя.
    LinearLayout messageStringSeparator; // Разделитель, чтобы сообщение не заполняло всю строку

    ConstraintLayout messageContainer;  // Контейнер сообщения для изменения фона и обработки
                                        // пользовтаельского нажатия

    public MessageViewHolder(@NonNull View itemView,
                             ChatView chatView) {
        super(itemView);

        constraintSet = new ConstraintSet();

        textView = itemView.findViewById(R.id.message_text);
        timeView = itemView.findViewById(R.id.message_time);
        singleMessageLine = itemView.findViewById(R.id.message_string);
        messageContainer = itemView.findViewById(R.id.message_container);
        messageStringSeparator = itemView.findViewById(R.id.message_string_separator);

        parentView = chatView;
    }

    public void bind(MessageModel messageOnBind,
                     boolean isSentByCurrentUser, int position){
        changeMessagePosition(isSentByCurrentUser);
        changeMessageBackground(isSentByCurrentUser);
        //changeMessageShape(isSentByCurrentUser);

        setText(messageOnBind.getText());
        setTime(messageOnBind.getTime().getTime());

        messageContainer.setOnClickListener(view ->{
            if (isSentByCurrentUser)
                parentView.showMessageMenu(messageOnBind, position, itemView);
        });
    }



    public void setTime(long time){
        DateFormat df = new SimpleDateFormat(MESSAGE_TIME_PATTERN);
        timeView.setText(df.format(time));
    }

    public void setText(String text){
        textView.setText(text);
    }

    public void changeMessagePosition(boolean isSentByCurrentUser){
        constraintSet.clone(messageContainer);
        //constraintSet.clear(R.id.message_text);
        //constraintSet.clear(R.id.message_time);

        if (isSentByCurrentUser){
            singleMessageLine.setGravity(Gravity.RIGHT);
            messageStringSeparator.setGravity(Gravity.RIGHT);
            //constraintSet.connect(R.id.message_text,ConstraintSet.RIGHT,
                   // R.id.message_container, ConstraintSet.RIGHT, 0 );
            //constraintSet.connect(R.id.message_time,ConstraintSet.RIGHT,
                    //R.id.message_container, ConstraintSet.RIGHT, 0);
        } else {
            singleMessageLine.setGravity(Gravity.LEFT);
            messageStringSeparator.setGravity(Gravity.LEFT);
            //constraintSet.connect(R.id.message_text,ConstraintSet.LEFT,
                    //R.id.message_container, ConstraintSet.LEFT, 0);
            //constraintSet.connect(R.id.message_time,ConstraintSet.LEFT,
                    //R.id.message_container, ConstraintSet.LEFT, 0);
        }
        constraintSet.applyTo(messageContainer);
    }

    public void changeMessageBackground(boolean isSentByCurrentUser){
        if (isSentByCurrentUser){
            messageContainer.setBackgroundResource(
                    R.drawable.current_user_message_shape);
            //messageContainer.setBackgroundColor(
                    //ContextCompat.getColor(itemView.getContext(),R.color.current_user_message_background));
        } else {
            messageContainer.setBackgroundResource(
                    R.drawable.other_user_message_shape);
            //messageContainer.setBackgroundColor(
                    //ContextCompat.getColor(itemView.getContext(),R.color.other_user_message_background));
        }
    }
}
