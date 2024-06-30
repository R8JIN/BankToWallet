package com.rest.template.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Demo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;
    @Lob
    @Column(name = "imagedata", length = 1000)
    byte[] imageData;
}
