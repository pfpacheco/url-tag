package br.com.lojadomecanico.urltag.api;

import br.com.lojadomecanico.urltag.service.UrlTagService;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/rest/api/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UrlTag {

    private final Logger log = LogManager.getLogger(this.getClass().getName());

    private final UrlTagService urlTagService;

    public UrlTag(UrlTagService urlTagService) {
        this.urlTagService = urlTagService;
    }

    @PostMapping(value = "/url_tag/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUrlTag(@RequestBody String body) throws ResponseStatusException {
        log.info("urlTagJSON: {}", body);
        JsonObject response = urlTagService.addUrlTag(body);
        return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
    }
    @GetMapping(value = "/url_tag/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUrlTag(@RequestParam String url_tag) throws ResponseStatusException {
        log.info("url_tag = {}", url_tag);
        JsonObject response = urlTagService.findUrlTag(url_tag);
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }
}
