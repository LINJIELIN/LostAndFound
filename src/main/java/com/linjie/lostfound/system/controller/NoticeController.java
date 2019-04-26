package com.linjie.lostfound.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.entity.ConstantsEnum;
import com.linjie.lostfound.framework.entity.Response;
import com.linjie.lostfound.framework.util.CommUtil;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.model.Notice;
import com.linjie.lostfound.system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.linjie.lostfound.framework.entity.StatusEnum;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/notice/list")
    public Response search(@RequestParam(value = "page") Integer page,
                           @RequestParam(value = "size") Integer size,
                           @RequestParam(value = "title") String title) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createTime","id"));
        Page<Notice> noticePage = noticeService.findAllByTitleContaining(title,pageable);
        JSONObject data = PageUtil.pageInfo(noticePage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }

    @GetMapping("/notice/home")
    public Response search() {
        Integer page = 1;
        Integer size = 4;
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createTime","id"));
        Page<Notice> noticePage = noticeService.findAllPage(pageable);
        JSONObject data = PageUtil.pageInfo(noticePage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }

    @GetMapping("/notice/{id}")
    public Response findById(@PathVariable("id") Long id){
        Notice notice = noticeService.findById(id);
        if(notice == null){
            return Response.factoryResponse(StatusEnum.NOTICE_ERROR_5002.getCode(),StatusEnum.NOTICE_ERROR_5002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),notice);
    }
    @GetMapping("/notice")
    public Response findAll(){
        List<Notice>noticeList =noticeService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),noticeList);
    }

    @DeleteMapping("/notice/{id}")
    public Response delete(@PathVariable("id") Long id) {
        Notice notice = noticeService.findById(id);
        if (notice == null) {
            return Response.factoryResponse(StatusEnum.NOTICE_ERROR_5002.getCode(),StatusEnum.NOTICE_ERROR_5002.getData());
        }
        try {
            noticeService.delete(notice);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }
    @PostMapping("/notice")
    public Response add(@RequestParam("title") String title,
                        @RequestParam("content") String content) {
        //1.数据校验
        if (CommUtil.isNullString(title,content)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.数据封装
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateTime(CommUtil.dateToString(new Date(),"yyyy-MM-dd"));

        //3.数据操作
        try {
            noticeService.save(notice);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }

}
