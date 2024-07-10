package com.example.lab7.repository;

import com.example.lab7.domain.FriendRequest;
import com.example.lab7.domain.Utilizator;

import java.util.Optional;

public interface FriendRequestRepository {
    Optional<FriendRequest> findOne(Long fromUserId, Long toUserId);
    Iterable<FriendRequest> findAll(String status);
    Optional<FriendRequest> save(FriendRequest friendRequest);
    Optional<FriendRequest> delete(Long id);
    void updateRequestType(Long fromUserId, Long toUserId, String newRequestType);
}
