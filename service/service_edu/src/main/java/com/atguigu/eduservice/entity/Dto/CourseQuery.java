package com.atguigu.eduservice.entity.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value="EduCourse对象", description="课程查询对象封装")
@Data
public class CourseQuery  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "课程标题")
    private String title;


    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "一级分类级ID")
    private String subjectParentId;


    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
}
