package com.hlhdidi.shop.web.product.service.upload;

import org.springframework.stereotype.Service;

import com.hlhdidi.shop.commons.utils.UploadUtils;
import com.hlhdidi.shop.interfaces.UploadService;
@Service("uploadService")
public class UploadServiceImpl implements UploadService{

	@Override
	public String upload(byte[] pic, String filename, long size) throws Exception {
		return UploadUtils.upload(pic, filename, size);
	}

}
