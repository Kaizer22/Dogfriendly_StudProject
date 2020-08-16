package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс временно заменяющий сообщения, поступающие через domain слой
 * @author dshebut@rambler.ru
 */
public class MessageProviderTemp {
    private static LinkedList<MessageModel> messages;

    public static void initProvider(){
        messages = new LinkedList<>();
        messages.add(new MessageModel("1","g9C670M67cT00qnljCisnyWG3Tj2",
                "2", "hello hello hello hello"));
        messages.add(new MessageModel("2","123",
                "g9C670M67cT00qnljCisnyWG3Tj2", "bye bye bye bye bye"));
        messages.add(new MessageModel("2","123",
                "g9C670M67cT00qnljCisnyWG3Tj2", "bye"));
    }

    public static List<MessageModel> getMessages(){
        return messages;
    }

    public static void addMessage(MessageModel message){
        messages.add(message);
    }

}
