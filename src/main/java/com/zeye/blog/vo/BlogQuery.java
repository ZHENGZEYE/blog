package com.zeye.blog.vo;

import com.zeye.blog.pojo.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/** 前端页面查询所需要的数据实体类 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogQuery {

    /**
     * 主键（id）、
     * 标题（title）、
     * 更新时间（updateTime）、
     * 是否推荐（recommend）、
     * 是否发布（published）、
     * 分类id（typeId）、
     * 分类（type）
     */
    private Long id;
    private String title;
    private Date updateTime;
    private Date createTime;
    private Boolean recommend;
    private Boolean published;
    private Long typeId;
    private Type type;

}
