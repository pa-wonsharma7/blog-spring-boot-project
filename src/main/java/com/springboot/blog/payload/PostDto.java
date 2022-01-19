package com.springboot.blog.payload;

import lombok.Data;

/***
 * PostDto is the object that will be used for displaying or sending response to the client only the required
 * details unlike responding with the entire entity.
 */
@Data
public class PostDto {

    private long id;
    private String title;
    private String description;
    private String content;

}
