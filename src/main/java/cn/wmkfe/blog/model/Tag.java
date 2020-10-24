package cn.wmkfe.blog.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class Tag implements Serializable {
    private Integer id;
    @NotEmpty(message = "标签名称不能为空")
    private String tagName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }
}