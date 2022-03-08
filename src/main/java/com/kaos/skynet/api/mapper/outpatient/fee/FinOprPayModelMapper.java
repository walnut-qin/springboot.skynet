package com.kaos.skynet.api.mapper.outpatient.fee;

import java.util.List;

import com.kaos.skynet.entity.outpatient.fee.FinOprPayModel;

public interface FinOprPayModelMapper {
    /**
     * 根据卡号/住院号查询交易记录
     * 
     * @param cardNo
     * @return
     */
    List<FinOprPayModel> queryPayModels(String cardNo);
}
