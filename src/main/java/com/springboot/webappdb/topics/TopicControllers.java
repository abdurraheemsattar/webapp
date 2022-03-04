package com.springboot.webappdb.topics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * RestController annotation is needed to make any class a rest controller
 * it is a Spring MVC annotation
 * a RestController class can have methods which map to URL requests,
 * so that the method executes when the user make a request to that URL
 */
@RestController
public class TopicControllers {

    Logger log = LoggerFactory.getLogger(TopicControllers.class);

    @Autowired
    private TopicService topicService;

    /*
     * takes a parameter which is the URL that the request needs to map to,
     * and returns to the URL whatever the method returns
     */

    @GetMapping("/topics")
    //@RequestMapping("/topics")
    public List<Topic> getAllTopics() {
        //log.trace("getAllTopics method called");

        log.info("getAllTopics method called");
        return topicService.getAllTopics();
    }

    @GetMapping("/topics/{id}")
    //@RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        //log.trace("getTopic method called");
        log.info("getTopic method called");
        return topicService.getTopic(id);
    }

    @PostMapping("/topics")
    //@RequestMapping(method = RequestMethod.POST, value = "/topics")
    public void addTopic(@RequestBody Topic topic) {
        //log.trace("addTopic method called");
        log.info("addTopic method called");
        topicService.addTopic(topic);
    }


    @PutMapping("/topics/{id}")
    //@RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
    public void updateTopic(@RequestBody Topic topic, @PathVariable String id) {
        //log.trace("updateTopic method called");
        log.info("updateTopic method called");
        topicService.updateTopic(topic, id);
    }


    @DeleteMapping("/topics/{id}")
    //@RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
    public void deleteTopic(@PathVariable String id) {
        //log.trace("deleteTopic method called");
        log.info("deleteTopic method called");
        topicService.deleteTopic(id);
    }


}






















