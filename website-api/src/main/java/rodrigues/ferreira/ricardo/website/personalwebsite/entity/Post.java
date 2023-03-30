package rodrigues.ferreira.ricardo.website.personalwebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.event.PostPublishedEvent;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends AbstractAggregateRoot<Post>{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="postId")
    protected Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "datetime")
    private Instant createdOn;

    @Column(columnDefinition = "datetime")
    private Instant updatedOn;

    private Integer voteCount = 0;

    private Long authorId;

    private String authorName;

    @Enumerated(EnumType.STRING)
    private StatusPost statusPost = StatusPost.DRAFT;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes = new HashSet<>();

    /*
     * OrphanRemoval está presente na maioria das anotações de relacionamento entre entidades,
     * e que serve para definir a forma como uma ação de remoção atribuída a um objeto terá impacto
     * sobre os objetos relacionados.
     * O OrphanRemoval marca entidades "filhas" para serem excluídas quando não tem qualquer outro
     * vinculo com uma entidade pai, por exemplo, quando você tem um carro em uma lista de carros
     * relacionados a um concessionária. Se a concessionári
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void published() {
        setStatusPost(StatusPost.PUBLISHED);
        setCreatedOn(Instant.now());
        registerEvent(new PostPublishedEvent(this));
    }

    public void unpublished() {
        setStatusPost(StatusPost.UNPUBLISHED);
        setUpdatedOn(Instant.now());
        registerEvent(new PostPublishedEvent(this));

    }

    public void draft() {
        setStatusPost(StatusPost.DRAFT);
        setCreatedOn(Instant.now());
        registerEvent(new PostPublishedEvent(this));

    }
}
