package com.linjie.lostfound.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linjie.lostfound.framework.entity.ConstantsEnum;

import javax.persistence.*;

/**
 * @ClassName: User
 * @Description: 用户表
 * @Author: MSI
 * @Date: 2018/12/27 19:28
 * @Vresion: 1.0.0
 **/
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Table(name = "user")
public class User {

    //@Id    @GeneratedValue()
    //@Column(name="id",length = 8)
    @Id
    @TableGenerator(name="USER_GENERATOR",
                    table="PK_GENERATOR",
                    pkColumnName="PK_COLUMN",
                    pkColumnValue="user",
                    valueColumnName="PK_VALUE",
                    initialValue=1000000,
                    allocationSize=1
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="USER_GENERATOR")
    @Column(name = "u_id")
    private Long id; //id
    @Column(name = "u_student_id")
    private String studentId;
    @Column(name = "u_account")
    private String account; //用户名
    //@JSONField(serialize = false)
    @Column(name = "u_password")
    private String password; //密码
    @Column(name = "u_name")
    private String name ; //姓名
    @Column(name = "u_phone")
    private String phone; //手机号
    @Column(name = "u_email")
    private String email; //邮箱
    @Column(name = "u_enabled")
    private Integer isEnabled = ConstantsEnum.ACCOUNT_STATUS_VALID.getValue(); //是否可用
    @Column(name = "u_online")
    private Integer isOnline = ConstantsEnum.ACCOUNT_IS_OFFLINE.getValue(); //是否在线
    @Column(name = "u_role_name")
    private String roleName = ConstantsEnum.USER_ROLE_USER.getData(); //角色名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
