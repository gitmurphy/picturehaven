package dev.petermurphy.picturehaven.tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> findAll();

    Optional<Tag> findById(Integer id);

    void create(Tag tag);

    void delete(Integer id);

    long count();

    List<Integer> findPicturesByTag(Integer id);
}
