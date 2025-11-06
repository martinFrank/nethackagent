package com.github.martinfrank.nethackagent.embedding;

import com.github.martinfrank.nethackagent.tools.wiki.WhiteList;
import com.google.gson.Gson;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WikiDocumentService {

    private final WikiDocumentRepository repository;

    @Autowired
    public WikiDocumentService(WikiDocumentRepository repository) {
        this.repository = repository;
    }

    public void save(WikiMdDocument wikiMdDocument) {
        WikiDocumentEntity entity = fromModel(wikiMdDocument);
        repository.save(entity);
    }

    public WikiMdDocument loadUrl(String url) {
        Optional<WikiDocumentEntity> result = repository.findByUrl(url);

        if(result.isPresent()){
            return fromEntity(result.get());
        }

        throw new IllegalArgumentException("url not in DB!");
    }

    private static WikiDocumentEntity fromModel(WikiMdDocument wikiMdDocument){
        WikiDocumentEntity entity = new WikiDocumentEntity();
        entity.setMdDocument(wikiMdDocument.text());
        entity.setUrl(wikiMdDocument.metadata().getString("url"));
        entity.setMetadata(new Gson().toJson(wikiMdDocument.metadata()));
        return entity;
    }

    private static WikiMdDocument fromEntity(WikiDocumentEntity entity){
        Metadata metadata = new Gson().fromJson(entity.getMetadata(), Metadata.class);
        String mdDocument = entity.getMdDocument();
        String url = metadata.getString("url");
        return new WikiMdDocument(url, mdDocument, metadata);
    }

}
