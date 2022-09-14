package br.com.lojadomecanico.urltag.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@SuppressWarnings("unused")
@Entity
@Table(name = "dbo.urltag")
public class UrlEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger Id;

    @Column(name = "url_fixa", columnDefinition = "bit default 0 not null")
    private int fixedUrl;

    @Column(name = "url_prod", columnDefinition = "varchar(1024) not null")
    private String prodUrl;

    @Column(name = "url_tag", columnDefinition = "varchar(1024)")
    private String tagUrl;

    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public int getFixedUrl() {
        return fixedUrl;
    }

    public void setFixedUrl(int fixedUrl) {
        this.fixedUrl = fixedUrl;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }
}
