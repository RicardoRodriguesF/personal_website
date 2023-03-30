package rodrigues.ferreira.ricardo.website.personalwebsite.repository.impl;


import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.CustomJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Random;
/*
* Esta é uma maneira de personalizar o nosso repository base, assim todos os outros
* vao herdar os metodos deste repository. Ou seja, em vez de herdar do JpaRepository
* ou SimpleJpaRepository herda deste repository personalizado
* */
public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;
    }

    /*Este metodo pode ser usado em todos os repositorios da minha app desde herdada esta class*/
    @Override
    public Optional<T> getRandom() {

        var jpql = "from " + getDomainClass().getName();

        List<T> entityList = entityManager.createQuery(jpql, getDomainClass())
                .getResultList();

        Random ran = new Random();
        int x = ran.nextInt(entityList.size());

        return Optional.ofNullable(entityList.get(x));
    }
}
