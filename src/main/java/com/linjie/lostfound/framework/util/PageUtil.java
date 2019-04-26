package com.linjie.lostfound.framework.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linjie.lostfound.system.model.Found;
import com.linjie.lostfound.system.model.Lost;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName: PageUtil
 * @Description: 分页工具类
 * @Author: MSI
 * @Date: 2019/1/7 15:31
 * @Vresion: 1.0.0
 **/
public class PageUtil {

    /**
     * @Author MSI
     * @Description 封装分页信息
     * @Date 2019/1/7 15:51
     * @Param [page]
     * @return com.alibaba.fastjson.JSONObject 
     **/       
    public static JSONObject pageInfo(Page page) {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        //封装分页信息

        jsonObject.put("total",page.getTotalElements()); //总条目数
        jsonObject.put("pageNum",page.getSize()); //本页条数
        jsonObject.put("currentPage",page.getNumber()); //页码
//        jsonObject.put("currentPage",page.getTotalPages()); //总页数
//        jsonObject.put("isLast",page.isLast()); //是否最后一页
//        jsonObject.put("isFirst",page.isFirst()); //是否第一页

        result.put("total",jsonObject); //写入分页信息
        result.put("data",page.getContent()); //写入内容
        return result;
    }

    public static JSONArray pageLostAndFound(List<Lost> lostList, List<Found> foundList) {
        JSONArray result = new JSONArray();
        //封装分页信息

        for (int i = 0; i < lostList.size(); i++) {
            result.add(lostList.get(i));
        }
        for (int i = 0; i < foundList.size(); i++) {
            result.add(foundList.get(i));
        }
        return result;
    }
}
