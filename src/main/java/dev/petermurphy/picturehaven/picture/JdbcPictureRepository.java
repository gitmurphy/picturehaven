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
        return jdbcClient.sql("SELECT * FROM pictures")
                .query(Picture.class)
                .list();
    }

    public List<Picture> findAllByArtist(Integer artist) {
        return jdbcClient.sql("SELECT * FROM pictures WHERE artist = :artist")
                .param("artist", artist)
                .query(Picture.class)
                .list();
    }

    public Optional<Picture> findById(Integer id) {
        return jdbcClient.sql("SELECT id,title,description,filepath,artist FROM pictures WHERE id = :id")
                .param("id", id)
                .query(Picture.class)
                .optional();
    }

    public void create(Picture picture) {
        var updated = jdbcClient.sql("INSERT INTO pictures(title,description,filepath,artist) VALUES(?,?,?,?)")
                .params(List.of(picture.title(),picture.description(),picture.filepath(),picture.artist()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + picture.title());
    }

    public void update(Picture picture, Integer id) {
        var updated = jdbcClient.sql("UPDATE pictures SET title = ?, description = ?, filepath = ?, artist = ? WHERE id = ?")
                .params(List.of(picture.title(),picture.description(),picture.filepath(),picture.artist(), id))
                .update();

        Assert.state(updated == 1, "Failed to update pictures " + picture.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM pictures where id = ?")
                .params(List.of(id))
                .update();

        Assert.state(updated == 1, "Failed to delete picture " + id);
    }

    public long count() {
        return jdbcClient.sql("SELECT * FROM pictures").query().listOfRows().size();
    }

    public long countPerArtist(Integer artistId) {
        return jdbcClient.sql("SELECT * FROM pictures WHERE artist = :artistId")
                .param("artistId", artistId)
                .query().listOfRows().size();
    }
	
	public List<Integer> findTagsByPicture(Integer id) {
        return jdbcClient.sql("SELECT tag FROM picture_tags WHERE picture = :id")
                .param("id", id)
                .query(Integer.class)
                .list();
    }

    public void addTagToPicture(Integer pictureId, Integer tagId) {
        var updated = jdbcClient.sql("INSERT INTO picture_tags(picture,tag) VALUES(?,?)")
                .params(List.of(pictureId,tagId))
                .update();

        Assert.state(updated == 1, "Failed to add tag to picture " + pictureId);
    }
}
