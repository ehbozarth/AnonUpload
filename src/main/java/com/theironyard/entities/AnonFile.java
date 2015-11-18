package com.theironyard.entities;

/**
 * Created by earlbozarth on 11/18/15.
 */

import javax.persistence.*;

@Entity
@Table(name = "files")
public class AnonFile {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    int id;
    //id will not be sent to json becasue it is not public

    @Column(nullable = false)
    public String originalName;

    @Column(nullable = false)
    public String name;
}
