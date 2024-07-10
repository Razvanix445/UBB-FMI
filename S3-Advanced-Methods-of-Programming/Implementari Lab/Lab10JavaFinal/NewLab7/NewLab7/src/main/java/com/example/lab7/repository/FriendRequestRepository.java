package com.example.lab7.repository;

import com.example.lab7.domain.FriendRequest;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.Pageable;

import java.util.Optional;

public interface FriendRequestRepository {
    Optional<FriendRequest> findOne(Long fromUserId, Long toUserId);
    Page<FriendRequest> findAll(Pageable pageable, Long userId);
    Iterable<FriendRequest> findAll(String status);
    Optional<FriendRequest> save(FriendRequest friendRequest);
    Optional<FriendRequest> delete(Long id);
    void updateRequestType(Long fromUserId, Long toUserId, String newRequestType);
}
