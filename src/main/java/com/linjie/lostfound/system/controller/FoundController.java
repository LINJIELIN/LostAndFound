package com.linjie.lostfound.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.configurer.Permission;
import com.linjie.lostfound.framework.entity.ConstantsEnum;
import com.linjie.lostfound.framework.entity.PermissionConstants;
import com.linjie.lostfound.framework.entity.Response;
import com.linjie.lostfound.framework.util.CommUtil;
import com.linjie.lostfound.framework.util.FileUtil;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.model.Found;
import com.linjie.lostfound.system.model.Lost;
import com.linjie.lostfound.system.service.FoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.linjie.lostfound.framework.entity.StatusEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FoundController {

    @Autowired
    FoundService foundService;

    @PutMapping("/found")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response updateFound(@RequestParam("id") String id,
                                   @RequestParam("title") String title,
                                   @RequestParam("type") String type,
                                   @RequestParam("foundTime") String foundTime,
                                   @RequestParam("location") String location,
                                   @RequestParam("information") String information,
                                   @RequestParam("contacts") String contacts,
                                   @RequestParam("phone") String phone,
                                   @RequestParam(value = "retrieve",defaultValue = "") String retrieve) {
        //2.1 验证数据完整性
        if (CommUtil.isNullValue(id,retrieve) || CommUtil.isNullString(title,type,foundTime,location,information,contacts,phone)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.2 验证拾物是否存在
        Found found = foundService.findById(Long.valueOf(id));
        if (found == null) {
            return Response.factoryResponse(StatusEnum.FOUND_ERROR_3002.getCode(),StatusEnum.FOUND_ERROR_3002.getData());
        }
        //3.修改拾物信息
        found.setTitle(title);
        found.setFoundTime(foundTime);
        found.setLocation(location);
        //found.setType(type);
        found.setInformation(information);
        found.setRetrieve("已经寻回".equals(retrieve) ? true : false);
        found.setContacts(contacts);
        found.setPhone(phone);
        try {
            foundService.save(found);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_UPDATE_FAIL.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    @GetMapping("/found/list")
    public Response searchDirector(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "name",defaultValue = "") String name,
                                   @RequestParam(value = "retrieve",defaultValue = "") String retrieve) {
        page = CommUtil.isNullValue(page) ? ConstantsEnum.PAGE_DEFT : page;
        size = CommUtil.isNullValue(size) ? ConstantsEnum.SIZE_DEFT : size;
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "foundTime"));
        Page<Found> foundPage = foundService.pageFound(name,retrieve,pageable);
        JSONObject data = PageUtil.pageInfo(foundPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }

    @GetMapping("/found/{id}")
    public Response findById(@PathVariable("id") Long id){
        Found found = foundService.findById(id);
        if(found == null){
            return Response.factoryResponse(StatusEnum.FOUND_ERROR_3002.getCode(),StatusEnum.FOUND_ERROR_3002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),found);
    }

    @GetMapping("/found")
    public Response findAll(){
        List<Found>foundList=foundService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),foundList);
    }
    @DeleteMapping("/found/{id}")
    public Response delete(@PathVariable("id") Long id) {
        Found found = foundService.findById(id);
        if (found == null) {
            return Response.factoryResponse(StatusEnum.FOUND_ERROR_3002.getCode(),StatusEnum.FOUND_ERROR_3002.getData());
        }
        try {
            foundService.delete(found);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }
    /**
     * @Author LINJIE
     * @Description 拾物新增
     * @Content: TODO
     * @Date 2019/3/13 15:25
     * @Param [title, foundTime, location, information, contacts, phone, multipartFile]
     * @return com.linjie.lostfound.framework.entity.Response
     **/
    @PostMapping("/found")
    public Response add(@RequestParam("title") String title,
                        @RequestParam("foundTime") String foundTime,
                        @RequestParam("type") String type,
                        @RequestParam("location") String location,
                        @RequestParam(value = "information",defaultValue = "--") String information,
                        @RequestParam("contacts") String contacts,
                        @RequestParam("phone") String phone,
                        @RequestParam("image") MultipartFile multipartFile) {
        //2.数据校验
        if (CommUtil.isNullString(title,foundTime,type,location,phone,contacts)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //3.数据封装
        Found found = new Found();
        found.setTitle(title);
        found.setFoundTime(foundTime);
        found.setLocation(location);
        //found.setType(type);
        found.setInformation(information);
        found.setContacts(contacts);
        found.setPhone(phone);
        //3.1保存图片，如果失败就返回文件保存失败
        String fileName = FileUtil.getImageString(multipartFile);
        if (CommUtil.isNullString(fileName)) {
            return Response.factoryResponse(StatusEnum.FOUND_ERROR_3003.getCode(),StatusEnum.FOUND_ERROR_3003.getData());
        }
        found.setImage(fileName);
        //4.数据操作
        try {
            foundService.save(found);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

}
