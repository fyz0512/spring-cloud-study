package org.finchley.study.dto;

import java.io.Serializable;

/**
 * 菜单传输数据容器
 * @author John Fang
 *
 */
public class MenuDTO implements Serializable{
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1000931588204732405L;

	
	
	private Long menuId;
    // 父菜单ID，一级菜单为0
    private Long parentId;
    // 菜单名称
    private String name;
    // 菜单URL
    private String url;
    //授权许可
    private String permit;
    // 类型 -1：固定菜单 0：目录 1：菜单 2：按钮 3：附加功能
    private Integer type;
    // 图标
    private String icon;
    //组件
    private String component;
    
    
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
