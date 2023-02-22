package rodrigues.ferreira.ricardo.website.personalwebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String body;
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDate createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
