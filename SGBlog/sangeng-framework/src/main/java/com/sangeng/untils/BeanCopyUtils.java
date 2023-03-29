package com.sangeng.untils;

import com.sangeng.demo.entity.Article;
import com.sangeng.demo.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils(){
    }

    public static <V> V copyBean(Object source,Class<V> clazz) {

        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性复制
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <K,V> List<V> copyBeanList(List<K> list,Class<V> clazz){

        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());

    }



}
