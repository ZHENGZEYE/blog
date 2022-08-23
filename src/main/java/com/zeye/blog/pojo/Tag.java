package com.zeye.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Tag  implements Serializable {

    private Long id;
    private String name;


}
