package com.jlb.service.Impl;

import com.jlb.mapper.DynamicStateMapper;
import com.jlb.model.DynamicState;
import com.jlb.service.DynamicStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicStateServiceImpl implements DynamicStateService {
    @Autowired
    DynamicStateMapper dynamicStateMapper;

    /**
     * 添加动态信息
     *
     * @param dynamicState 动态信息
     */
    @Override
    public void addDynamicState(DynamicState dynamicState) {
        dynamicStateMapper.addDynamicState(dynamicState);
    }

    /**
     * 删除动态信息
     *
     * @param type   动态类型
     * @param typeId 动态类型对应id
     */
    @Override
    public void deleteDynamicStateByTypeAndTypeId(int type, int typeId) {
        dynamicStateMapper.deleteDynamicStateByTypeAndTypeId(type,typeId);
    }

    /**
     * 删除动态信息
     *
     * @param dynamicState
     */
    @Override
    public void deleteDynamicStateByDynamicState(DynamicState dynamicState) {
        dynamicStateMapper.deleteDynamicStateByDynamicState(dynamicState);
    }

    /**
     * 通过用户的id查询用户的信息
     *
     * @param id 用户的id
     * @return 用户的动态
     */
    @Override
    public List<DynamicState> queryDynamicStateByMemberId(int id) {
        return dynamicStateMapper.queryDynamicStateByMemberId(id);
    }

    /**
     * 查询所有的动态信息
     * @return 所有动态信息
     */
    @Override
    public List<DynamicState> queryDynamicStateAll() {
        return dynamicStateMapper.queryDynamicStateAll();
    }

    /**
     * 通过类型id查找对应类型的所有动态信息
     *
     * @param type   动态类型
     * @param typeId 类型对应id
     * @return 动态信息
     */
    @Override
    public List<DynamicState> queryDynamicStateByTypeAndTypeId(int type, int typeId) {
        return dynamicStateMapper.queryDynamicStateByTypeAndTypeId(type, typeId);
    }
}
