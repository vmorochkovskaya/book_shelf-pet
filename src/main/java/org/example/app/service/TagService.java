package org.example.app.service;

import org.example.app.entity.tag.Tag;
import org.example.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepo) {
        this.tagRepository = tagRepo;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Integer id) {
        return tagRepository.findTagById(id);
    }
}
