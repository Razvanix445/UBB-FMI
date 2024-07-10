package com.example.lab7.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReplyMessage extends Message {

    private Message repliedMessage;

    public ReplyMessage(Utilizator from, List<Utilizator> to, String message, LocalDateTime date, Message repliedMessage) {
        super(from, to, message, date);
        this.repliedMessage = repliedMessage;
    }

    public Message getRepliedMessage() {
        return repliedMessage;
    }

    public void setRepliedMessage(Message repliedMessage) {
        this.repliedMessage = repliedMessage;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "repliedMessage=" + repliedMessage +
                '}';
    }
}