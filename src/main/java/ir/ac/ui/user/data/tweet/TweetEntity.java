package ir.ac.ui.user.data.tweet;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long senderId;

    private Long sentDate;

    private String message;
}
