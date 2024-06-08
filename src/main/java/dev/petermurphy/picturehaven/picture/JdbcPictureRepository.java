package dev.petermurphy.picturehaven.picture;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPictureRepository implements PictureRepository {
	
	private final JdbcClient jdbcClient;

    public JdbcPictureRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Picture> findAll() {
        return jdbcClient.sql("select * from pictures")
                .query(Picture.class)
                .list();
    }

    public Optional<Picture> findById(Integer id) {
        return jdbcClient.sql("SELECT id,title,description,filepath FROM pictures WHERE id = :id")
                .param("id", id)
                .query(Picture.class)
                .optional();
    }

    public void create(Picture picture) {
        var updated = jdbcClient.sql("INSERT INTO pictures(title,description,filepath) values(?,?,?)")
                .params(List.of(picture.title(),picture.description(),picture.filepath()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + picture.title());
    }

    public void update(Picture picture, Integer id) {
        var updated = jdbcClient.sql("update pictures set title = ?, description = ?, filepath = ? where id = ?")
                .params(List.of(picture.title(),picture.description(),picture.filepath(), id))
                .update();

        Assert.state(updated == 1, "Failed to update pictures " + picture.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from pictures where id = ?")
                .params(List.of(id))
                .update();

        Assert.state(updated == 1, "Failed to delete picture " + id);
    }

    public long count() {
        return jdbcClient.sql("select * from pictures").query().listOfRows().size();
    }

    public void saveAll(List<Picture> pictures) {
        pictures.stream().forEach(this::create);
    }

    public void save(Picture picture) {
        create(picture);
    }
}