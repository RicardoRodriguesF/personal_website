package rodrigues.ferreira.ricardo.website.personalwebsite.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/*
* É uma class de especificacoes para o meu repositorio, basicamente crio filtros para as minhas
* consulta à bd*/
public class PostSpecs {

    public static Specification<Post> voteMoreThanOne() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("voteCount"), BigDecimal.ZERO);
    }

    public static Specification<Post> newerPosts() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"),
                Instant.now().minus(1, ChronoUnit.DAYS));
    }
}
