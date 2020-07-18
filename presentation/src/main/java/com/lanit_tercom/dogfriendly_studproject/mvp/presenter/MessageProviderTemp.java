package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс временно заменяющий сообщения, поступающие через domain слой
 * @author dshebut@rambler.ru
 */
public class MessageProviderTemp {

    public static List<MessageModel> getMessages(){
        LinkedList<MessageModel> messages = new LinkedList<>();
        messages.add(new MessageModel("1","g9C670M67cT00qnljCisnyWG3Tj2",
                "2", "hello hello hello hello", System.currentTimeMillis()));
        messages.add(new MessageModel("2","123",
                "g9C670M67cT00qnljCisnyWG3Tj2", "bye bye bye bye bye", System.currentTimeMillis() + 1000000));
        messages.add(new MessageModel("2","123",
                "g9C670M67cT00qnljCisnyWG3Tj2", "bye", System.currentTimeMillis() + 1000000));
        return messages;
    }
}
