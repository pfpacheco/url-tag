package br.com.lojadomecanico.urltag.repository.intf;

import br.com.lojadomecanico.urltag.entity.UrlEntity;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;
import java.util.List;

public interface UrlTagRepositoryIntf extends Repository<UrlEntity, BigInteger> {

    UrlEntity save(UrlEntity url);

    UrlEntity findById(BigInteger id);

    UrlEntity findByTagUrl(String urlTag);

    List<UrlEntity> findAll();

    Long count();

    void deleteById(BigInteger id);

    void delete(UrlEntity urlEntity);
}
