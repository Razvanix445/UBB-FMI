package com.example.lab7.domain;

import java.util.Objects;

public class FriendRequest {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String requestType;

    public FriendRequest(Long id, Long fromUserId, Long toUserId, String requestType) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.requestType = requestType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(fromUserId, that.fromUserId) && Objects.equals(toUserId, that.toUserId) && Objects.equals(requestType, that.requestType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromUserId, toUserId, requestType);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
