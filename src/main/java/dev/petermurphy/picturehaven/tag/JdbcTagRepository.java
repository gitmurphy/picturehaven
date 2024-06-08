package dev.petermurphy.picturehaven.tag;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTagRepository implements TagRepository {

    private final JdbcClient jdbcClient;

    public JdbcTagRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Tag> findAll() {
        return jdbcClient.sql("SELECT * FROM tags")
                .query(Tag.class)
                .list();
    }

    public Optional<Tag> findById(Integer id) {
        return jdbcClient.sql("SELECT id,name FROM tags WHERE id = :id")
                .param("id", id)
                .query(Tag.class)
                .optional();
    }

    public void create(Tag tag) {
        var updated = jdbcClient.sql("INSERT INTO tags(name) VALUES(?)")
                .params(List.of(tag.name()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + tag.name());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM tags where id = ?")
                .params(List.of(id))
                .update();

        Assert.state(updated == 1, "Failed to delete tag " + id);
    }

    public long count() {
        return jdbcClient.sql("SELECT * FROM tags").query().listOfRows().size();
    }
}
