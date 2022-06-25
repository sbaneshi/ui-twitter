package ir.ac.ui.user.data.user;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
@Table(indexes = @Index(columnList = "phone"))
public class UserEntity {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    private ArrayList<Long> followers;
}
