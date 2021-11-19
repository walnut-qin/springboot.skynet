package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.Escort;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortMapper {
    /**
     * 获取陪护证
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    Escort GetEscort(String escortNo);

    /**
     * 获取陪护证列表
     * 
     * @param escortNo 陪护人卡号
     * @return
     */
    List<Escort> GetEscortsByHelperCardNo(String cardNo);
}
