package com.hlhdidi.shop.pojo.product;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    /**
     * ID����Ʒ���
     */
    private Long id;

    /**
     * Ʒ��ID
     */
    private Long brandId;

    /**
     * ��Ʒ���
     */
    private String name;

    /**
     * ���� ��λ:��
     */
    private Float weight;

    /**
     * �Ƿ���Ʒ:0:��Ʒ,1:��Ʒ
     */
    private Boolean isNew;

    /**
     * �Ƿ�����:0,�� 1:��
     */
    private Boolean isHot;

    /**
     * �Ƽ� 1�Ƽ� 0 ���Ƽ�
     */
    private Boolean isCommend;

    /**
     * ���¼�:0�� 1��
     */
    private Boolean isShow;

    /**
     * ��ƷͼƬ��
     */
    private String imgUrl;

    /**
     * �Ƿ�ɾ��:0ɾ��,1,ûɾ��
     */
    private Boolean isDel;

    /**
     * ��Ʒ����
     */
    private String description;

    /**
     * ��װ�嵥
     */
    private String packageList;

    /**
     * ��ɫ��
     */
    private String colors;

    /**
     * �ߴ缯
     */
    private String sizes;

    /**
     * ���ʱ��
     */
    private Date createTime;
   /**
    * 亢余字段展示第一张图片
    */
    private String firstImg;
    /**
     * 
     * @return
     */
    private Float price;
    private String[] images;
    
	public String[] getImages() {
		if(imgUrl!=null)
			this.images=imgUrl.split(",");
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public String getFirstImg() {
		if(this.imgUrl!=null) {
			this.firstImg=imgUrl.split(",")[0];
		}
		return firstImg;
}

	private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsCommend() {
        return isCommend;
    }

    public void setIsCommend(Boolean isCommend) {
        this.isCommend = isCommend;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors == null ? null : colors.trim();
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes == null ? null : sizes.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", brandId=").append(brandId);
        sb.append(", name=").append(name);
        sb.append(", weight=").append(weight);
        sb.append(", isNew=").append(isNew);
        sb.append(", isHot=").append(isHot);
        sb.append(", isCommend=").append(isCommend);
        sb.append(", isShow=").append(isShow);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", isDel=").append(isDel);
        sb.append(", description=").append(description);
        sb.append(", packageList=").append(packageList);
        sb.append(", colors=").append(colors);
        sb.append(", sizes=").append(sizes);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}