package com.linjie.lostfound.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.configurer.Permission;
import com.linjie.lostfound.framework.entity.ConstantsEnum;
import com.linjie.lostfound.framework.entity.PermissionConstants;
import com.linjie.lostfound.framework.entity.Response;
import com.linjie.lostfound.framework.entity.StatusEnum;
import com.linjie.lostfound.framework.util.CommUtil;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.model.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SortController
 * Description: TODO
 * author: LINJIE
 * date: 2019/4/21 11:04
 * version V1.0
 */
@RestController
@CrossOrigin("*")
public class SortController {

    @Autowired
    com.linjie.sortfound.system.service.SortService sortService;

    @PostMapping("/sort")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response add(@RequestParam("name") String name) {
        if (CommUtil.isNullString(name)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        if (sortService.findByName(name) == null) {
            return Response.factoryResponse(StatusEnum.SORT_ERROR_7001.getCode(),StatusEnum.SORT_ERROR_7001.getData());
        }
        Sort sort = new Sort();
        sort.setName(name);
        try {
            sortService.save(sort);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }
    
    @DeleteMapping("/sort/{id}")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response delete(@PathVariable Long id) {
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        Sort sort = sortService.findById(id);
        if (sort == null) {
            return Response.factoryResponse(StatusEnum.SORT_ERROR_7002.getCode(),StatusEnum.SORT_ERROR_7002.getData());
        }
        try {
            sortService.save(sort);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    @PutMapping("/sort")
    @Permission(PermissionConstants.USER_ROLE_ADMIN)
    public Response update(@RequestParam("id") Long id,
                           @RequestParam("name") String name) {
        if (CommUtil.isNullString(name) || CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        Sort sort = sortService.findById(id);
        if (sort == null) {
            return Response.factoryResponse(StatusEnum.SORT_ERROR_7002.getCode(),StatusEnum.SORT_ERROR_7002.getData());
        }
        sort.setName(name);
        try {
            sortService.save(sort);
        }catch (Exception e) {
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

    @GetMapping("/sort/{id}")
    public Response findById(@PathVariable("id") Long id){
        if (CommUtil.isNullValue(id)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        Sort sort = sortService.findById(id);
        if(sort == null){
            return Response.factoryResponse(StatusEnum.SORT_ERROR_7002.getCode(),StatusEnum.SORT_ERROR_7002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),sort);
    }

    @GetMapping("/sort")
    public Response findAll(){
        List<Sort> sortList = sortService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),sortList);
    }

    @GetMapping("/sort/list")
    public Response search(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "name",defaultValue = "") String name) {
        page = CommUtil.isNullValue(page) ? ConstantsEnum.PAGE_DEFT : page;
        size = CommUtil.isNullValue(size) ? ConstantsEnum.SIZE_DEFT : size;
        Pageable pageable = PageRequest.of(page-1, size, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
        Page<Sort> sortPage = sortService.pageSort(name,pageable);
        JSONObject data = PageUtil.pageInfo(sortPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }
}
