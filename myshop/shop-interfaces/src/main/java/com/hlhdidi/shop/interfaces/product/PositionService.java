package com.hlhdidi.shop.interfaces.product;

import java.util.List;

import com.hlhdidi.shop.pojo.product.Position;

public interface PositionService {

	List<Position> findPositionsByParent(long parent);

	Position findPositionsById(long parseLong);

}
