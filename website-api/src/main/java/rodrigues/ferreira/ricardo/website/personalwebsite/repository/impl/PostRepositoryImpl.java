package rodrigues.ferreira.ricardo.website.personalwebsite.repository.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.PostRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.PostRepositoryQueries;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.spec.PostSpecs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    /*Corrige o erro circular, só vai instanciar qnd for preciso*/
    @Autowired @Lazy
    private PostRepository repository;

    @Override
    public List<Post> findTitleFilterDate(String title, Instant createdOn, Instant updatedOn) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);

        Root<Post> root = query.from(Post.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(title)) {
            predicates.add(builder.like(root.get("title"), "%" + title + "%"));
        }

        if (createdOn != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdOn"), createdOn));
        }

        if (updatedOn != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("updatedOn"), updatedOn));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    /*É como se fosse um filtro, e posso agregar varios filtros no mesmo metodo, e assim
    * reaproveitando codigo.*/
    public List<Post> findNewPostsVoteData() {
        return repository.findAll(PostSpecs.voteMoreThanOne().and(PostSpecs.newerPosts()));
    }
}
