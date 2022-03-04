package com.springboot.webappdb.course;

import com.springboot.webappdb.topics.Topic;
import com.springboot.webappdb.topics.TopicControllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseControllers {


    Logger log = LoggerFactory.getLogger(CourseControllers.class);


    @Autowired
    private CourseService courseService;

    @RequestMapping("/topics/{id}/courses")
    public List<Course> getAllCourses(@PathVariable String id) {
        log.info("getAllCourses method called");
        return courseService.getAllCourses(id);
    }

    @RequestMapping("/topics/{topicId}/courses/{id}")
    public Course getCourse(@PathVariable String id) {
        log.info("getCourse method called");
        return courseService.getCourse(id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses")
    public void addCourse(@RequestBody Course course, @PathVariable String topicId) {
        log.info("addCourse method called");
        course.setTopic(new Topic(topicId, "", ""));
        courseService.addCourse(course);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{id}")
    public void updateTopic(@RequestBody Course course, @PathVariable String topicId, @PathVariable String id) {
        log.info("updateTopic method called");
        course.setTopic(new Topic(topicId, "", ""));
        courseService.updateCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{id}")
    public void deleteTopic(@PathVariable String id) {
        log.info("deleteTopic method called");
        courseService.deleteCourse(id);
    }


}






















