package extclasses.final_project_spring.service;

import extclasses.final_project_spring.entity.Tag;
import extclasses.final_project_spring.exception.CustomException;
import extclasses.final_project_spring.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<String> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::getTagsByLocale)
                .collect(Collectors.toList());
    }

    private String getTagsByLocale(Tag tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    public List<Tag> getTagsFromStringArray(String[] tags) {
        log.info("get tags from array {}", Arrays.toString(tags));
        return Arrays.stream(tags)
                .map(x -> getByNameAndLocale(x)
                        .orElseThrow(() -> new CustomException("tag.not.found")))
                .collect(Collectors.toList());
    }

    private Optional<Tag> getByNameAndLocale(String tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tagRepository.findByName(tag) : tagRepository.findByNameUa(tag);
    }
}