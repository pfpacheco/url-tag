package br.com.lojadomecanico.urltag.service.intf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigInteger;

@SuppressWarnings("unused")
public interface UrlTagServiceIntf {

    JsonObject addUrlTag(String urlTagJSON);

    JsonObject findUrlTagById(BigInteger id);

    JsonObject findUrlTag(String urlTagJSON);

    JsonObject findAllUrlTags();

    long count();

    void deleteUrlTagById(BigInteger id);

    void deleteUrlTag(String urlTagJSON);
}
