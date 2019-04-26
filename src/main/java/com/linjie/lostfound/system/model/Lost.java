package com.linjie.lostfound.system.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Lost {
    @Id
    @TableGenerator(name="LOST_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="lost",
            valueColumnName="PK_VALUE",
            initialValue=2000000,
            allocationSize=1
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="LOST_GENERATOR")
    @Column(name = "l_id")
    private Long id; //主键
    @Column(name = "l_title")
    private String title; //标题
//    @Column(name = "l_type")
//    private String type; //类型
    @Column(name = "l_lost_time")
    private String lostTime;//丢失时间   单词1单词2  ，lost + time  单词2首字母大写 = lostTime
    @Column(name = "l_location")
    private String location; //丢失地点
    @Column(name = "l_retrieve")
    private boolean retrieve = false; //是否找回 ,false表示未被找回
    @Column(name = "l_information")
    private String information; //详细信息
    @Column(name = "l_contacts")
    private String contacts; //联系人
    @Column(name = "l_phone")
    private String phone; //电话
    @Column(name = "l_image",columnDefinition = "mediumtext")
    private String image; //图片存放路径

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,optional = true)
    private Sort sort;

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

    public String getLostTime() {
        return lostTime;
    }

    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isRetrieve() {
        return retrieve;
    }

    public void setRetrieve(boolean retrieve) {
        this.retrieve = retrieve;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
