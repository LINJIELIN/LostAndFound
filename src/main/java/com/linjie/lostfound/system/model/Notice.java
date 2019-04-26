package com.linjie.lostfound.system.model;

import javax.persistence.*;

@Entity
public class Notice {
    @Id
    @TableGenerator(name="NOTICE_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="notice",
            valueColumnName="PK_VALUE",
            initialValue=5000000,
            allocationSize=1
    )
    @GeneratedValue(strategy= GenerationType.TABLE, generator="NOTICE_GENERATOR")
    @Column(name = "n_id")
    private Long id; //主键
    @Column(name = "n_title")
    private String title; //标题
    @Column(name = "n_content")
    private String content; //内容
    @Column(name = "n_create_time")
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
