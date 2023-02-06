package rodrigues.ferreira.ricardo.website.personalwebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "datetime")
    private LocalDate createdOn;

    @Column(columnDefinition = "datetime")
    private LocalDate updatedOn;

   /* todo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;*/

    /* todo
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();*/
    /*



    * OrphanRemoval está presente na maioria das anotações de relacionamento entre entidades,
    * e que serve para definir a forma como uma ação de remoção atribuída a um objeto terá impacto
    * sobre os objetos relacionados.
    * O OrphanRemoval marca entidades "filhas" para serem excluídas quando não tem qualquer outro
    * vinculo com uma entidade pai, por exemplo, quando você tem um carro em uma lista de carros
    * relacionados a um concessionária. Se a concessionári
    */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
