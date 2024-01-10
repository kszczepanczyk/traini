package pl.polsl.traini.service.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.TagRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.tag.Tag;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    public List<Tag> getListByIds(List<Long> ids) {
        return tagRepository.findAllById(ids);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
