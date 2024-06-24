package dev.petermurphy.picturehaven.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcTagRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcTagRepositoryTest {

    @Autowired
    JdbcTagRepository jdbcTagRepository;
    long dbTagCount;

    // Get current tag count before each test
    @BeforeEach
    void setUp() {
        dbTagCount = jdbcTagRepository.count();
    }

    @Test
    void shouldFindAllTags() {
        List<Tag> tags = jdbcTagRepository.findAll();
        assertEquals(dbTagCount, tags.size());
    }

    @Test
    void shouldCreateTag() {
        Tag tag = new Tag(null, "Test Tag");
        jdbcTagRepository.create(tag);
        List<Tag> tags = jdbcTagRepository.findAll();
        assertEquals(dbTagCount + 1, tags.size());
    }

    @Test
    void shouldDeleteTag() {
        Tag tag = new Tag(null, "Test Tag");
        // insert a new tag to test the delete method
        jdbcTagRepository.create(tag);
        List<Tag> tags = jdbcTagRepository.findAll();
        Tag tagToDelete = tags.get(tags.size() - 1);
        // delete the tag
        jdbcTagRepository.delete(tagToDelete.id());
        List<Tag> updatedTags = jdbcTagRepository.findAll();
        assertEquals(dbTagCount, updatedTags.size());
    }
}
