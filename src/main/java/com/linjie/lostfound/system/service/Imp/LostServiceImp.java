package com.linjie.lostfound.system.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.entity.PermissionConstants;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.dao.FoundRepository;
import com.linjie.lostfound.system.dao.LostRepository;
import com.linjie.lostfound.system.dao.UserRepository;
import com.linjie.lostfound.system.model.Found;
import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.model.User;
import com.linjie.lostfound.system.service.FoundService;
import com.linjie.lostfound.system.service.LostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostServiceImp implements LostService {

    @Autowired
    FoundService foundService;

    @Autowired
    LostRepository lostRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public JSONArray findHistory(String contacts) {
        String name = userRepository.findByAccountOrPhone(contacts).getName();
        List<Lost> lostList = findAllByContacts(name);
        List<Found> foundList = foundService.findAllByContacts(name);
        JSONArray data = PageUtil.pageLostAndFound(lostList,foundList);
        return data;
    }

    @Override
    public boolean permission(String account, Long id) {
        User user = userRepository.findByAccount(account);
        /**1判断登录用户是否是管理员**/
        if (user.getRoleName().equals(PermissionConstants.USER_ROLE_ADMIN)) {
            return true;
        }
        /**2判断登录用户是否是需要删除信息的联系人*/
        String contacts = "";
        /**2.1获得联系人*/
        if (id/1000000 == 2) {
             contacts = this.findById(id).getContacts();
        }else {
             contacts = foundService.findById(id).getContacts();
        }
        /**2.2判断登录用户的用户名和联系人*/
        if (contacts != null && contacts.equals(user.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public List<Lost> findAllByContacts(String contacts) {
        return lostRepository.findAllByContacts(contacts);
    }

    @Override
    public Page<Lost> pageLost(String name, String retrieve, Pageable pageable) {
        return "".equals(retrieve)
                ? lostRepository.findAllByTitleContaining(name,pageable)
                : lostRepository.findAllByTitleContainingAndRetrieve(name,Boolean.valueOf(retrieve),pageable);
    }

    @Override
    public Page<Lost> pageLost(Pageable pageable) {
        return lostRepository.findAll(pageable);
    }

    @Override
    public List<Lost> findAll() {
        return lostRepository.findAll();
    }

    /**
     * @Author LINJIE
     * @Description  查询单条
     * @Content: TODO
     * @Date 2019/3/11 21:24
     * @Param
     * @return
     **/
    @Override
    public Lost findById(Long id) {
        Optional<Lost> lostOption = lostRepository.findById(id);
        return lostOption.isPresent() ? lostOption.get() : null;
    }

    @Override
    public void delete(Lost lost) {
        lostRepository.delete(lost);
    }

    @Override
    public void save(Lost lost) {
        lostRepository.save(lost);
    }
}
