package br.com.lojadomecanico.urltag.service;

import br.com.lojadomecanico.urltag.entity.UrlEntity;
import br.com.lojadomecanico.urltag.repository.UrlTagRepository;
import br.com.lojadomecanico.urltag.service.handler.UrlTagHandler;
import br.com.lojadomecanico.urltag.service.intf.UrlTagServiceIntf;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
@Service
public class UrlTagService implements UrlTagServiceIntf {

    private final UrlTagRepository urlTagRepository;

    private final Logger log = LogManager.getLogger(this.getClass().getName());

    private static final UrlTagHandler urlTagHandler = new UrlTagHandler();

    public UrlTagService(UrlTagRepository urlTagRepository) {
        this.urlTagRepository = urlTagRepository;
    }

    @Override
    public JsonObject addUrlTag(String urlTagJSON) {
        JsonObject urlTagResponse;
        Gson gson = urlTagHandler.getGson();
        try {
            log.info("urlTagJSON: {}", urlTagJSON);
            UrlEntity urlEntity = urlTagHandler.getUrlTagFromJson(urlTagJSON, gson);
            urlEntity = urlTagRepository.save(urlEntity);
            urlTagResponse = urlTagHandler.parseUrlTagToJson(urlEntity);
        } catch (Exception e) {
            log.error("Erro no servico addUrlTag {}", e.getMessage());
            return urlTagHandler.parseErrorMessageToJson("Erro no servico addUrlTag");
        }
        return urlTagResponse;
    }

    @Override
    public JsonObject findUrlTagById(BigInteger id) {
        JsonObject urlTagResponse;
        try {
            log.info("urlTagJSON id: {}", id);
            UrlEntity urlEntity = urlTagRepository.findById(id);
            urlTagResponse = urlTagHandler.parseUrlTagToJson(urlEntity);
        } catch (Exception e) {
            log.error("Erro no servico findUrlTagById {}", e.getMessage());
            return urlTagHandler.parseErrorMessageToJson("Erro no servico findUrlTagById");
        }
        return urlTagResponse;
    }

    @Override
    public JsonObject findUrlTag(String urlTag) {
        JsonObject urlTagResponse;
        try {
            log.info("url_tag: {}", urlTag);
            UrlEntity urlEntity = urlTagRepository.findByTagUrl(urlTag);
            urlTagResponse = urlTagHandler.parseUrlTagToJson(urlEntity);
        } catch (Exception e) {
            log.error("Erro no servico fundUrlTag {}", e.getMessage());
            return urlTagHandler.parseErrorMessageToJson("Erro no servico findUrlTag");
        }
        return urlTagResponse;
    }

    @Override
    public JsonObject findAllUrlTags() {
        JsonObject urlTagResponse;
        JsonArray urlTags = new JsonArray();
        try {
            List<UrlEntity> urlEntities = urlTagRepository.findAll();
            urlEntities.forEach(urlEntity -> {
                JsonObject urlTag = urlTagHandler.parseUrlTagToJson(urlEntity);
                urlTags.add(JsonParser.parseString(urlTag.getAsString()));
            });
            urlTagResponse = new JsonObject();
            urlTagResponse.add("urlTags", urlTags);
        } catch (Exception e) {
            log.error("Erro no servico findAllUrlTags {}", e.getMessage());
            return urlTagHandler.parseErrorMessageToJson("Erro no servico findAllUrlTags");
        }
        return urlTagResponse;
    }

    @Override
    public long count() {
        long count = 0L;
        try {
            count = urlTagRepository.count();
        } catch (Exception e) {
            log.error("Erro ao recuperar o numero de elementos na tabela {}", e.getMessage());
        }
        return count;
    }

    @Override
    public void deleteUrlTagById(BigInteger id) {
        try {
            urlTagRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Erro ao deletar urlTag pelo id {}", e.getMessage());
        }
    }

    @Override
    public void deleteUrlTag(String urlTagJSON) {
        Gson gson = urlTagHandler.getGson();
        try {
            UrlEntity urlEntity = urlTagHandler.getUrlTagFromJson(urlTagJSON, gson);
            urlTagRepository.delete(urlEntity);
        } catch (Exception e) {
            log.error("Erro ao deletar urlTag {}", e.getMessage());
        }
    }
}
