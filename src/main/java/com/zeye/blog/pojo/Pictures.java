package com.zeye.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 相册实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pictures implements Serializable {

    private Long id;
    private String picturename;
    private String picturetime;
    private String pictureaddress;
    private String picturedescription;
}
