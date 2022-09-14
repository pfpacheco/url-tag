package br.com.lojadomecanico.urltag.service.handler;

import br.com.lojadomecanico.urltag.entity.UrlEntity;
import com.google.gson.*;

@SuppressWarnings("unused")
public class UrlTagHandler {

    public Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return gsonBuilder.create();
    }

    public JsonObject parseUrlTagToJson(UrlEntity urlEntity) {
        JsonObject urlTagJson = new JsonObject();
        urlTagJson.addProperty("id", urlEntity.getId());
        urlTagJson.addProperty("url_fixa", urlEntity.getFixedUrl() != 0);
        urlTagJson.addProperty("url_prod", urlEntity.getProdUrl());
        urlTagJson.addProperty("url_tag", urlEntity.getTagUrl());
        return urlTagJson;
    }

    public UrlEntity getUrlTagFromJson(String urlTagJSON, Gson gson) {
        UrlEntity urlEntity;
        JsonObject urlTagJson = JsonParser.parseString(urlTagJSON).getAsJsonObject();
        urlEntity = gson.fromJson(urlTagJson, UrlEntity.class);
        urlEntity.setFixedUrl(!urlTagJson.get("url_fixa").getAsBoolean() ? 0 : 1);
        if (!urlTagJson.get("url_fixa").getAsBoolean()) {
            UrlShrinkHandler urlShrinkHandler = new UrlShrinkHandler();
            String urlShrunk = urlShrinkHandler.urlShrunk(urlTagJson.get("url_prod").getAsString());
            urlEntity.setTagUrl(urlShrunk);
        } else {
            urlEntity.setTagUrl(urlTagJson.get("url_tag").getAsString());
        }
        urlEntity.setProdUrl(urlTagJson.get("url_prod").getAsString());
        return urlEntity;
    }

    public JsonObject parseErrorMessageToJson(String message) {
        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("message", message);
        return errorJson;
    }
}
