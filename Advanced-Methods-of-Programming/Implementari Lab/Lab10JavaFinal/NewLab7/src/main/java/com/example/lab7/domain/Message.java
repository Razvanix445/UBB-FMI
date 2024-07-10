package com.example.lab7.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message extends Entity<Long> {

    private Utilizator from;
    private List<Utilizator> to;
    private String message;
    private LocalDateTime date;
    private Long repliedMessageId;

    public Message(Utilizator from, List<Utilizator> to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getRepliedMessageId() {
        return repliedMessageId;
    }

    public void setRepliedMessageId(Long repliedMessageId) {
        this.repliedMessageId = repliedMessageId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "ID=" + getId() +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
