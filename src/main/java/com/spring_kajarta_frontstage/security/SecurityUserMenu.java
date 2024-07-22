package com.spring_kajarta_frontstage.security;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class SecurityUserMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
    private Boolean checked;
    private Integer order;
    private List<SecurityUserMenu> children;

    public SecurityUserMenu() {
        super();
        this.children = new LinkedList<>();
    }

    public SecurityUserMenu(String name, String url, Integer order, Boolean checked) {
        this();
        this.name = name;
        this.url = url;
        this.order = order;
        this.checked = checked;
    }

    public void addChild(SecurityUserMenu menu) {
        this.children.add(menu);
    }

}
