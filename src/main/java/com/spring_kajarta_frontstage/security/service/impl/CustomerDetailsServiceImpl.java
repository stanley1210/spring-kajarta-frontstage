package com.spring_kajarta_frontstage.security.service.impl;

import com.kajarta.demo.model.Customer;
import com.spring_kajarta_frontstage.security.SecurityUser;
import com.spring_kajarta_frontstage.security.service.CustomerDetailsService;
import com.spring_kajarta_frontstage.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public SecurityUser loadUserByUsername(String username) {
        // 管理员
        Customer customer = customerService.getByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("使用者名稱或密碼錯誤");
        }

        // 如果有設計後台權限就需要
        // 角色權限
//        List<SimpleGrantedAuthority> adminAuths = getAdminAuths(admin);
        // 沒有設計權限，還是要給空集合
        List<SimpleGrantedAuthority> adminAuths = Collections.emptyList();
        // 角色菜單
//        List<SecurityUserMenu> adminMenus = getAdminMenus(admin);

        // 可用性 :true:可用 false:不可用
//        boolean enabled = AdminStatusEnum.CANCEL.getCode() != admin.getStatus();
        boolean enabled = true;
        // 過期性 :true:沒過期 false:過期
        boolean accountNonExpired = true;
        // 有效性 :true:憑證有效 false:憑證無效
        boolean credentialsNonExpired = true;
        // 鎖定性 :true:未鎖定 false:已鎖定
//        boolean accountNonLocked = AdminStatusEnum.FROZEN.getCode() != admin.getStatus();
        boolean accountNonLocked = true;
        // GA驗證：是否開啟
//        boolean googleEnable = Optional.ofNullable(admin.getGoogleEnable()).orElse(false);

        return new SecurityUser(Long.parseLong(customer.getId().toString()),
                customer.getAccount(),
                customer.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                adminAuths);
    }

    /**
     * 管理員權限
     */
//    private List<SimpleGrantedAuthority> getAdminAuths(Admin admin) {
//        if (admin == null || AdminStatusEnum.NORMAL.getCode() != admin.getStatus()) {
//            return Collections.emptyList();
//        }
//
//        List<AdminAuth> list = adminAuthService.getByRoleId(admin.getRoleId());
//
//        if (CollectionUtils.isEmpty(list)) {
//            return Collections.emptyList();
//        }
//
//        List<SimpleGrantedAuthority> auths = new ArrayList<>();
//        for (AdminAuth auth : list) {
//            auths.add(new SimpleGrantedAuthority(auth.getCode()));
//        }
//        return auths;
//    }

    /**
     * 管理員菜單
     */
//    private List<SecurityUserMenu> getAdminMenus(Admin admin) {
//        if (admin == null || AdminStatusEnum.NORMAL.getCode() != admin.getStatus()) {
//            return Collections.emptyList();
//        }
//
//        List<AdminMenu> adminMenus = adminMenuService.getByRoleId(admin.getRoleId());
//
//        if (CollectionUtils.isEmpty(adminMenus)) {
//            return Collections.emptyList();
//        }
//
//        List<SecurityUserMenu> menus = new ArrayList<>();
//        Map<Long, SecurityUserMenu> menuMap = new HashMap<>();
//
//        for (AdminMenu menu : adminMenus) {
//            if (menu == null) {
//                continue;
//            }
//            SecurityUserMenu userMenu = new SecurityUserMenu(menu.getName(), menu.getUrl(), menu.getOrder(), false);
//            if (menu.getParentId() <= 0) {
//                menus.add(userMenu);
//            } else {
//                SecurityUserMenu parent = menuMap.get(menu.getParentId());
//                if (parent != null) {
//                    parent.addChild(userMenu);
//                }
//            }
//            menuMap.put(menu.getId(), userMenu);
//        }
//
//        // 主菜單排序
//        menus.sort(Comparator.comparing(SecurityUserMenu::getOrder));
//        // 子菜單排序
//        for (SecurityUserMenu menu : menus) {
//            if (menu.getChildren() != null) {
//                menu.getChildren().sort(Comparator.comparing(SecurityUserMenu::getOrder));
//            }
//        }
//
//        return menus;
//    }

}
