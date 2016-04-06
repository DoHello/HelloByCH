package com.derbysoft.controller.sys;

import java.util.List;

import com.derbysoft.entity.sys.ItemStyle;


public class SYS_Bar {

	private String  name;
    
	private String type;
	
	private ItemStyle itemStyle = new ItemStyle();
    private List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/*name: '不知名的派出所',
	type: 'bar',
	itemStyle: {
	    normal: {
	      
	        label: {
	            show: true,
	            position: 'top',
	            formatter: '{a}\n{c}件'*/

	public ItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}


}
