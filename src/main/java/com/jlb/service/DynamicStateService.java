package com.jlb.service;


import com.jlb.model.DynamicState;

import java.util.List;

public interface DynamicStateService {

    /**
     * 添加动态信息
     *
     * @param dynamicState 动态信息
     */
    void addDynamicState(DynamicState dynamicState);

    /**
     * 删除动态信息
     *
     * @param type   动态类型
     * @param typeId 动态类型对应id
     */
    void deleteDynamicStateByTypeAndTypeId(int type, int typeId);

    /**
     * 删除动态信息
     *
     * @param dynamicState
     */
    void deleteDynamicStateByDynamicState(DynamicState dynamicState);

    /**
     * 通过用户的id查询用户的信息
     *
     * @param id 用户的id
     * @return 用户的动态
     */
    List<DynamicState> queryDynamicStateByMemberId(int id);

    /**
     * 查询所有的动态信息
     *
     * @return 所有动态信息
     */
    List<DynamicState> queryDynamicStateAll();

    /**
     * 通过类型id查找对应类型的所有动态信息
     *
     * @param type   动态类型
     * @param typeId 类型对应id
     * @return 动态信息
     */
    List<DynamicState> queryDynamicStateByTypeAndTypeId(int type, int typeId);

}
