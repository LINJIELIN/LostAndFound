package com.linjie.lostfound.system.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Journalism {

    @Id
    @TableGenerator(name="JOURNALISM_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="journalism",
            valueColumnName="PK_VALUE",
            initialValue=4000000,
            allocationSize=1
    )
    @GeneratedValue(strategy= GenerationType.TABLE, generator="JOURNALISM_GENERATOR")
    @Column(name = "j_id")
    private Long id;
    @Column(name = "j_type")
    private String type;//类型
    @Column(name = "j_content")
    private String content;//内容
    @Column(name = "j_initiator")
    private String initiator;//发起人
    @Column(name = "j_createTime")
    private Date createTime;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
