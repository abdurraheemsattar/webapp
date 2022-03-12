package com.springboot.webappdb.course;


import com.springboot.webappdb.topics.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    private Topic topic;


}
