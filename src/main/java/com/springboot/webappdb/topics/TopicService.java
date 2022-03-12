package com.springboot.webappdb.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"topics"})
public class TopicService {

    //marks the varialble as something that needs depedency injection
    @Autowired
    private TopicRepository topicRepository;


    @Cacheable
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(topics::add);
        return topics;
    }

    @Cacheable
    public Topic getTopic(String id) {
        return topicRepository.findById(id).orElse(null);
    }


    //@CachePut( cacheNames = {"topics"},key = "#topic")
    @CacheEvict(value = "topics", key = "#topic")
    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    //@CacheEvict(key = "#topic")
    public void updateTopic(Topic topic, String id) {
        topicRepository.save(topic);
    }

    //@CacheEvict(key = "#id")
    public void deleteTopic(String id) {
        topicRepository.deleteById(id);
    }

}


























