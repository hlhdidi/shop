package com.hlhdidi.shop.commons.utils;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class UploadUtils {
	public static String upload(byte[] pic, String filename, long size) throws Exception {
		//初始化.
		ClassPathResource resource=new ClassPathResource("track_client.conf");//在classpath下搜索文件,
		//获取文件的真实路径.初始化.
		ClientGlobal.init(resource.getClassLoader().getResource("track_client.conf").getPath());
		//TrackerClient
		TrackerClient client=new TrackerClient();
		//获取与tracker的连接
		TrackerServer server = client.getConnection();
		//storeageClient用于存储数据
		//连接存储服务器
		StorageClient1 client1=new StorageClient1(server, null);
		String ex = FilenameUtils.getExtension(filename);
		//NameValuePair[]数组可以向FASTDFS存储一些文件的信息.可以直接通过client1.getMetaData获取对应的信息
		NameValuePair[] pair=new NameValuePair[3];
		pair[0]=new NameValuePair("filename",filename);
		pair[1]=new NameValuePair("fileext",ex);
		pair[2]=new NameValuePair("filesize",String.valueOf(size));
		String path = client1.upload_file1(pic,ex, pair);
		
		return path;
	}

}
