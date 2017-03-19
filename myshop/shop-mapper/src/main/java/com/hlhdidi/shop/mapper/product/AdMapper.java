package com.hlhdidi.shop.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlhdidi.shop.pojo.product.Ad;
import com.hlhdidi.shop.pojo.product.AdQuery;

public interface AdMapper {
    int countByExample(AdQuery example);

    int deleteByExample(AdQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Ad record);

    int insertSelective(Ad record);

    List<Ad> selectByExample(AdQuery example);

    Ad selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Ad record, @Param("example") AdQuery example);

    int updateByExample(@Param("record") Ad record, @Param("example") AdQuery example);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);
}