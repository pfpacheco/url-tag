package br.com.lojadomecanico.urltag.repository;

import br.com.lojadomecanico.urltag.entity.UrlEntity;
import br.com.lojadomecanico.urltag.repository.intf.UrlTagRepositoryIntf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public class UrlTagRepository implements UrlTagRepositoryIntf {

    private final Logger log = LogManager.getLogger(this.getClass().getName());

    public UrlTagRepository() {

    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UrlEntity save(UrlEntity url) {
        try {
            url = entityManager.merge(url);
        } catch (PersistenceException e) {
            log.error("Erro ao persistir a URL >> {} ", e.getMessage() );
        } finally {
            if(entityManager.isOpen()) {
                try {
                    entityManager.flush();
                } catch (TransactionException e) {
                    log.error("Erro ao liberar recursos do entityManager {} ", e.getMessage());
                }
            }
        }
        return url;
    }

    @Override
    public UrlEntity findById(BigInteger id) {
        UrlEntity urlEntity = null;
        try {
            urlEntity = entityManager.find(UrlEntity.class, id);
        } catch (PersistenceException e) {
            log.error("Erro ao localizar a url pelo Id");
        }
        return urlEntity;
    }

    @Override
    public UrlEntity findByTagUrl(String urlTag) {
        UrlEntity urlEntity = null;
        try {
            CriteriaQuery<UrlEntity> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(UrlEntity.class);
            Root<UrlEntity> rootUrlEntity = criteriaQuery.from(UrlEntity.class);
            Predicate predicate = entityManager.getCriteriaBuilder().equal(rootUrlEntity.get("tagUrl"), urlTag);
            urlEntity = entityManager.createQuery(criteriaQuery.where(predicate)).getSingleResult();
        } catch (QueryException e) {
            log.error("Erro ao buscar pela url_tag >> {}", e.getMessage());
        }
        return urlEntity;
    }

    @Override
    public List<UrlEntity> findAll() {
        List<UrlEntity> urls = new ArrayList<>();
        try {
            CriteriaQuery<UrlEntity> query = entityManager.getCriteriaBuilder().createQuery(UrlEntity.class);
            urls = entityManager.createQuery(query.select(query.from(UrlEntity.class))).getResultList();
        } catch (QueryException e) {
            log.error("Erro ao selecionar lista de urls >> {}", e.getMessage());
        }
        return urls;
    }

    @Override
    public Long count() {
        long count = 0L;
        try {
            CriteriaQuery<UrlEntity> query = entityManager.getCriteriaBuilder().createQuery(UrlEntity.class);
            count = entityManager.createQuery(query.select(query.from(UrlEntity.class))).getResultList().size();
        } catch (QueryException e) {
            log.error("Erro ao verificar a quantidade de objetos na tabela >> {} ", e.getMessage());
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(BigInteger id) {
        UrlEntity urlEntity;
        try {
            urlEntity = entityManager.find(UrlEntity.class, id);
            entityManager.remove(urlEntity);
        } catch (PersistenceException e) {
            log.error("Erro ao remover a url pelo id {} ", e.getMessage());
        } finally {
            if (entityManager.isOpen()) {
                try {
                    entityManager.flush();
                } catch (TransactionException e) {
                    log.error("Erro ao liberar recursos do entityManager >> {} ", e.getMessage());
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UrlEntity urlEntity) {
        try {
            entityManager.remove(urlEntity);
        } catch (PersistenceException e) {
            log.error("Erro ao deletar a url >> {}", e.getMessage());
        } finally {
            if (entityManager.isOpen()) {
                try {
                    entityManager.flush();
                } catch (TransactionException e) {
                    log.error("Erro ao liberar recursos do entityManager >> {} ", e.getMessage());
                }
            }
        }
    }
}
