package com.linjie.lostfound.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.entity.ConstantsEnum;
import com.linjie.lostfound.framework.entity.Response;
import com.linjie.lostfound.framework.util.CommUtil;
import com.linjie.lostfound.framework.util.FileUtil;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.model.Found;
import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.service.FoundService;
import com.linjie.lostfound.system.service.LostService;
import com.linjie.lostfound.framework.entity.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class LostController {

    @Autowired
    LostService lostService;

    @Autowired
    FoundService foundService;

    @Autowired
    com.linjie.sortfound.system.service.SortService sortService;

    /**
     * @Author LINJIE
     * @Description //TODO 删除之前的校验
     * @Date 15:56 2019/4/25
     * @Param [name]
     * @return com.linjie.lostfound.framework.entity.Response
     **/
    @DeleteMapping("/delete2/{id}")
    public Response delete2(@PathVariable Long id) {
        Lost lost = new Lost();
        Found found = new Found();

        if (id/1000000 == 2) {
            lost = lostService.findById(id);
            try {
                lostService.delete(lost);
                return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
            }catch (Exception e) {
                return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
            }
        }else {
            found = foundService.findById(id);
            try {
                foundService.delete(found);
                return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
            }catch (Exception e) {
                return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
            }
        }
    }

    /**
     * @Author LINJIE
     * @Description //TODO 删除之前的校验
     * @Date 15:56 2019/4/25
     * @Param [name]
     * @return com.linjie.lostfound.framework.entity.Response
     **/
    @PostMapping("/permission")
    public Response permission(@RequestParam(value = "account") String account,
                               @RequestParam(value = "id") Long id) {
        return lostService.permission(account,id)
                ? Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData())
                : Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9003.getCode(),StatusEnum.SYSTEM_ERROR_9003.getData());

    }

    @GetMapping("/lost/list")
    public Response searchDirector(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "name",defaultValue = "") String name,
                                   @RequestParam(value = "retrieve",defaultValue = "") String retrieve) {
        page = CommUtil.isNullValue(page) ? ConstantsEnum.PAGE_DEFT : page;
        size = CommUtil.isNullValue(size) ? ConstantsEnum.SIZE_DEFT : size;
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "lostTime"));
        Page<Lost> lostPage = lostService.pageLost(name,retrieve,pageable);
        JSONObject data = PageUtil.pageInfo(lostPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }

    @GetMapping("/lost")
    public Response findAll() {
        List<Lost> lostList = lostService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),lostList);
    }

    @GetMapping("/lost/{id}")
    public Response findById(@PathVariable("id") Long id){
        Lost lost = lostService.findById(id);
        if(lost == null){
            return Response.factoryResponse(StatusEnum.LOST_ERROR_2002.getCode(),StatusEnum.LOST_ERROR_2002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),lost);
    }

    @DeleteMapping("/lost/{id}")
    public Response delete(@PathVariable("id") Long id) {
        Lost lost = lostService.findById(id);
        if (lost == null) {
            return Response.factoryResponse(StatusEnum.LOST_ERROR_2002.getCode(),StatusEnum.LOST_ERROR_2002.getData());
        }
        try {
            lostService.delete(lost);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    /**
     * @Author LINJIE
     * @Description 失物添加
     * @Content: 步骤：1、数据接收
     *                  2、数据校验
     *                  3、数据封装
     *                  4、数据操作
     * @Date 2019/3/12 21:12
     * @Param [title, lostTime, location, information, contacts, phone, multipartFile]
     * @return com.linjie.lostfound.framework.entity.Response
     **/
    @PostMapping("/lost")
    public Response add(@RequestParam("title") String title,
                        @RequestParam("type") Long sortId,
                        @RequestParam("lostTime") String lostTime,
                        @RequestParam("location") String location,
                        @RequestParam(value = "information",defaultValue = "--") String information,
                        @RequestParam("contacts") String contacts,
                        @RequestParam("phone") String phone,
                        @RequestParam("image") MultipartFile multipartFile) {
        //2.数据校验
        if (CommUtil.isNullString(title,lostTime,location,phone,contacts)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //3.数据封装
        Lost lost = new Lost(); //实例化
        //定义
        //类型 变量名  是什么
        //Lost lost1 = new Lost();
        lost.setTitle(title);
        //lost.setType(type);
        lost.setLostTime(lostTime);
        lost.setLocation(location);
        lost.setInformation(information);
        lost.setContacts(contacts);
        lost.setPhone(phone);
        lost.setRetrieve(false);
        //3.1保存图片，如果失败就返回文件保存失败
        String fileName = FileUtil.getImageString(multipartFile);
        if (CommUtil.isNullString(fileName)) {
            return Response.factoryResponse(StatusEnum.LOST_ERROR_2003.getCode(),StatusEnum.LOST_ERROR_2003.getData());
        }
        com.linjie.lostfound.system.model.Sort sort = sortService.findById(sortId);
        lost.setSort(sort);
        lost.setImage(fileName);
        //4.数据操作
        try {
            lostService.save(lost);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    @PutMapping("/lost")
    public Response update(@RequestParam("id") Long id,
                           @RequestParam("title") String title,
                           @RequestParam("type") String type,
                           @RequestParam("lostTime") String lostTime,
                           @RequestParam("location") String location,
                           @RequestParam(value = "information",defaultValue = "--") String information,
                           @RequestParam("contacts") String contacts,
                           @RequestParam(value = "retrieve",defaultValue = "") String retrieve,
                           @RequestParam("phone") String phone) {
        //2.数据校验
        if (CommUtil.isNullValue(id,retrieve) || CommUtil.isNullString(title,lostTime,type,location,phone,contacts)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //3.数据封装
        Lost lost = lostService.findById(id);
        if (lost == null) {
            return Response.factoryResponse(StatusEnum.LOST_ERROR_2002.getCode(),StatusEnum.LOST_ERROR_2002.getData());
        }
        lost.setTitle(title);
        //lost.setType(type);
        lost.setLostTime(lostTime);
        lost.setLocation(location);
        lost.setInformation(information);
        lost.setContacts(contacts);
        lost.setPhone(phone);
        lost.setRetrieve(retrieve.equals("已经寻回") ? true : false);
        //4.数据操作
        try {
            lostService.save(lost);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    @GetMapping("/lostAndFound")
    public Response lostAndFound(@RequestParam(value = "contacts") String contacts){
        JSONArray data = lostService.findHistory(contacts);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }
}