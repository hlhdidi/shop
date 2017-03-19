package com.hlhdidi.shop.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlhdidi.shop.pojo.product.Position;
import com.hlhdidi.shop.pojo.product.PositionQuery;

public interface PositionMapper {
    int countByExample(PositionQuery example);

    int deleteByExample(PositionQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    List<Position> selectByExample(PositionQuery example);

    Position selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionQuery example);

    int updateByExample(@Param("record") Position record, @Param("example") PositionQuery example);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}