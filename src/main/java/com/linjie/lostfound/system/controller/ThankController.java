package com.linjie.lostfound.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.framework.entity.Response;
import com.linjie.lostfound.framework.util.CommUtil;
import com.linjie.lostfound.framework.util.PageUtil;
import com.linjie.lostfound.system.model.Thank;
import com.linjie.lostfound.system.service.ThankService;
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
public class ThankController {
    @Autowired
    ThankService thankService;

    @GetMapping("/thank/list")
    public Response search(@RequestParam(value = "page") Integer page,@RequestParam(value = "size") Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Thank> thankPage = thankService.findAll(pageable);
        JSONObject data = PageUtil.pageInfo(thankPage);
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),data);
    }
    @GetMapping("/thank/{id}")
    public Response findById(@PathVariable("id") Long id){
        Thank thank = thankService.findById(id);
        if(thank == null){
            return Response.factoryResponse(StatusEnum.THANK_ERROR_6002.getCode(),StatusEnum.THANK_ERROR_6002.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),thank);
    }
    @GetMapping("/thank")
    public Response findAll(){
        List<Thank> thankList=thankService.findAll();
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),thankList);
    }
    @DeleteMapping("/thank/{id}")
    public Response delete(@PathVariable("id") Long id) {
        Thank thank = thankService.findById(id);
        if (thank == null) {
            return Response.factoryResponse(StatusEnum.THANK_ERROR_6002.getCode(),StatusEnum.THANK_ERROR_6002.getData());
        }
        try {
            thankService.delete(thank);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_DELETE_FAIL.getCode(),StatusEnum.RET_DELETE_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }
    /**
     * @Author LINJIE
     * @Description 感谢增加
     * @Content: TODO
     * @Date 2019/3/13 15:26
     * @Param [title, foundTime, location, information, contacts, phone, multipartFile]
     * @return com.linjie.lostfound.framework.entity.Response
     **/
    @PostMapping("/thank")
    public Response add(@RequestParam("title") String title,
                        @RequestParam("content") String content,
                        @RequestParam("time") String time) {
        //1.数据校验
        if (CommUtil.isNullString(title,content,time)) {
            return Response.factoryResponse(StatusEnum.SYSTEM_ERROR_9002.getCode(),StatusEnum.SYSTEM_ERROR_9002.getData());
        }
        //2.数据封装
        Thank thank = new Thank();

        thank.setTitle(title);
        thank.setContent(content);
        thank.setTime(time);

        //3.数据操作
        try {
            thankService.save(thank);
        }catch (Exception e){
            return Response.factoryResponse(StatusEnum.RET_INSERT_FAIL.getCode(),StatusEnum.RET_INSERT_FAIL.getData());
        }
        return Response.factoryResponse(StatusEnum.RESPONSE_OK.getCode(),StatusEnum.RESPONSE_OK.getData());
    }
}
