package org.example.app.repository;

import org.example.app.entity.genre.Genre;
import org.example.app.entity.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findTagById(Integer id);

    Page<Tag> findTagById(Integer id, Pageable page);

    List<Tag> findTagsByBooksSlugIn(String[] slugs);

}
