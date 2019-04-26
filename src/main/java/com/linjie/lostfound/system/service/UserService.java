package com.linjie.lostfound.system.service;

import com.linjie.lostfound.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description: 用户服务层
 * @Author: MSI
 * @Date: 2019/1/2 14:21
 * @Vresion: 1.0.0
 **/
public interface UserService {
    
    /**
     * @Author LINJIE
     * @Description //TODO 根据用户名查询是否具有删除权限
     * @Date 16:02 2019/4/25
     * @Param [account]
     * @return com.linjie.lostfound.system.model.User
     **/
    User findByAccount(String account);

    /**
     * @Author MSI
     * @Description 根据用户名或者电话号码查询是否存在此用户
     * @Date 2019/1/9 8：59
     * @Param [name]
     * @return com.inchwisp.tale.system.model.User 
     **/       
    User findByAccountOrPhone(String keyWord);

    /**
     * @Author MSI
     * @Description 分页查询
     * @Content: TODO
     * @Date 2019/2/7 20:02
     * @Param [name, pageable]
     * @return org.springframework.data.domain.Page<com.inchwisp.tale.system.model.User>
     **/
    Page<User> pageUser(String name, Pageable pageable);

    void saveUser(User user);

    void deleteUser(User user);

    User findById(long id);

    /**
     * @Author MSI
     * @Description 列表查询
     * @Content: TODO
     * @Date 2019/2/7 20:03
     * @Param []
     * @return java.util.List<com.inchwisp.tale.system.model.User>
     **/
    List<User> findAll();
}
