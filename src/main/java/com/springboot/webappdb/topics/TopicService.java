package com.springboot.webappdb.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    //marks the varialble as something that needs depedency injection
    @Autowired
    private TopicRepository topicRepository;


//    private List<Topic> topics = new ArrayList<>(Arrays.asList(
//                new Topic("spring1", "1st other string", "Spring Framework Description1"),
//                new Topic("spring2", "2nd other string", "Spring Framework Description2"),
//                new Topic("spring3", "3rd other string", "Spring Framework Description3")
//    ));


    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(topics::add);
        return topics;
    }

    public Topic getTopic(String id) {
        return topicRepository.findById(id).orElse(null);
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void updateTopic(Topic topic, String id) {
        topicRepository.save(topic);
    }

    public void deleteTopic(String id) {
        topicRepository.deleteById(id);
    }
}


























