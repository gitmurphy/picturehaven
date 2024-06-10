package dev.petermurphy.picturehaven.picture;

import java.util.List;
import java.util.Optional;

public interface PictureRepository {

    List<Picture> findAll();

    List<Picture> findAllByArtist(Integer artist);

    Optional<Picture> findById(Integer id);

    void create(Picture picture);

    void update(Picture picture, Integer id);

    void delete(Integer id);

    long count();
	
	List<Integer> findTagsByPicture(Integer id);

    void addTagToPicture(Integer pictureId, Integer tagId);
}
