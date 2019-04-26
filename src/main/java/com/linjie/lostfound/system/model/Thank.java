package com.linjie.lostfound.system.model;

import javax.persistence.*;

@Entity
public class Thank {
    @Id
    @TableGenerator(name="THANK_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="thank",
            valueColumnName="PK_VALUE",
            initialValue=6000000,
            allocationSize=1
    )
    @GeneratedValue(strategy= GenerationType.TABLE, generator="THANK_GENERATOR")
    @Column(name = "t_id")
    private Long id;
    @Column(name = "t_title")
    private String title; //标题
    @Column(name = "t_content")
    private String content;//内容
    @Column(name = "t_time")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
}
