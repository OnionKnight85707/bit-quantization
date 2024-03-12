package com.mzwise.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员日志操作模块枚举
 * @author: David Liang
 * @create: 2022-07-21 15:17
 */
@AllArgsConstructor
@Getter
public enum AdminLogModuleEnum {

    UPDATE_PARTNER(1, "修改合伙人"),
    ADD_BALANCE(2, "充币"),
    SUBTRACT_BALANCE(3, "减币"),
    STRATEGY_UPDATE(4, "自设指标-修改"),
    STRATEGY_CLOSE_POSITION(5, "自设指标-平仓"),
    STRATEGY_ONE_CLICK_CLOSE(6, "自设指标一键平仓"),
    SMART_COIN_SELECTION_ADD(7, "智能选币-增加"),
    SMART_COIN_SELECTION_UPDATE(8, "智能选币-修改"),
    SMART_COIN_SELECTION_DELETE(9, "智能选币-删除"),
    SMART_COIN_SELECTION_ENABLE_DISABLE(10, "智能选币-启用禁用"),
    QUANT_CONF(11, "量化配置"),
    ADMIN_ADD(12, "管理员添加"),
    ADMIN_UPDATE(13, "管理员修改"),
    ADMIN_DELETE(14, "管理员删除"),
    ROLE_MANAGE(15, "角色管理"),
    MENU_MANAGE(16, "菜单管理"),
    RESOURCE_MANAGE(17, "资源管理"),
    ANNOUNCEMENT_MANAGE(18, "公告管理"),
    FINANCE_MANAGE(19, "财经管理"),
    CAROUSEL_MANAGE(20, "轮播图管理"),
    POSITION_CLOSE_POSITION(21, "仓位管理-平仓");

    private final Integer value;
    private final String name;

}
