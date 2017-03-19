package com.hlhdidi.shop.interfaces.product;

import java.util.List;

import com.hlhdidi.shop.pojo.product.Ad;

public interface AdService {

	List<Ad> findAdByPositionId(long parseLong);

	String findAdByPositionIdStr(long i);

}
