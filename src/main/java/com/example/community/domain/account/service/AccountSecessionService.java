package com.example.community.domain.account.service;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repo.CommentRepository;
import com.example.community.domain.guestBook.entity.GuestBook;
import com.example.community.domain.guestBook.repo.GuestBookRepository;
import com.example.community.domain.notification.entity.Notification;
import com.example.community.domain.notification.repo.NotificationRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postLike.entity.PostLike;
import com.example.community.domain.postLike.repo.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountSecessionService {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final GuestBookRepository guestBookRepository;
    private final CommentRepository commentRepository;

    private final NotificationRepository notificationRepository;

    private final PostLikeRepository postLikeRepository;

    private final SecurityContextUtil securityContextUtil;

    public void secession() {

        Account account = securityContextUtil.currentAccount();

        List<Post> postList = postRepository.findByAccount(account);
        List<Comment> commentList = commentRepository.findByAccount(account);
        List<PostLike> postLikeList = postLikeRepository.findByAccount(account);
        List<GuestBook> ownerGuestBook = guestBookRepository.findByOwner(account);
        List<GuestBook> guestBook = guestBookRepository.findByGuest(account);
        List<Notification> notificationsToAccount = notificationRepository.findByToAccount(account);
        List<Notification> notificationsFromAccount = notificationRepository.findByFromAccount(account);

        commentRepository.deleteAll(commentList);

        postLikeRepository.deleteAll(postLikeList);
        postRepository.deleteAll(postList);
        guestBookRepository.deleteAll(ownerGuestBook);
        guestBookRepository.deleteAll(guestBook);

        notificationRepository.deleteAll(notificationsToAccount);
        notificationRepository.deleteAll(notificationsFromAccount);

        accountRepository.delete(account);
    }

}
