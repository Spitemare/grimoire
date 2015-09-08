package org.grimoire.model;

import static org.grimoire.util.Maps.*;
import java.util.Date;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.json.ComparableVersionDeserializer;
import org.grimoire.json.ComparableVersionSerializer;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class DataFeed extends BaseEntity {

    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Game game;

    @Column(nullable = false)
    @Type(type = "org.grimoire.model.support.ComparableVersionUserType")
    @JsonDeserialize(using = ComparableVersionDeserializer.class)
    @JsonSerialize(using = ComparableVersionSerializer.class)
    private ComparableVersion version;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ComparableVersion getVersion() {
        return version;
    }

    public void setVersion(ComparableVersion version) {
        this.version = version;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        //@formatter:off
        return unmodifiableMapOf(
                entry("name", name),
                entry("game", game.getName()),
                entry("version", version));
        //@formatter:on
    }

}
