package com.hlhdidi.shop.web.product.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlhdidi.shop.interfaces.product.PositionService;
import com.hlhdidi.shop.mapper.product.PositionMapper;
import com.hlhdidi.shop.pojo.product.Position;
import com.hlhdidi.shop.pojo.product.PositionQuery;
@Service("positionService")
public class PositionServiceImpl implements PositionService{

	@Autowired
	private PositionMapper positionMapper;
	
	@Override
	public List<Position> findPositionsByParent(long parent) {
		PositionQuery query=new PositionQuery();
		query.createCriteria().andParentIdEqualTo(parent);
		return positionMapper.selectByExample(query);
	}

	@Override
	public Position findPositionsById(long parseLong) {
		return positionMapper.selectByPrimaryKey(parseLong);
	}

}
