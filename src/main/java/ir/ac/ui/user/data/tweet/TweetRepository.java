package ir.ac.ui.user.data.tweet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

    List<TweetEntity> findAllBySenderId(long senderUserId);
}
