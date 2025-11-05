package com.github.martinfrank.nethackagent.embedding;

import jakarta.persistence.*;

@Entity
@Table (name = "wikidocument")
public class WikiDocumentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "url")
    private String url;

    @Column (name = "metadata")
    private String metadata;

    @Column(name = "mddocument")
    private String mdDocument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMdDocument() {
        return mdDocument;
    }

    public void setMdDocument(String mdDocument) {
        this.mdDocument = mdDocument;
    }

    @Override
    public String toString() {
        return "WikiDocumentEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", metadata='" + metadata + '\'' +
                ", mddocument='" + mdDocument + '\'' +
                '}';
    }
}
