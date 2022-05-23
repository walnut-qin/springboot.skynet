package com.kaos.skynet.api.data.mapper.inpatient.escort.annex;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexCheck;

import lombok.Data;

public interface EscortAnnexCheckMapper {
    /**
     * 查询附件审核记录
     * 
     * @param annexNo 附件号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortAnnexCheck queryAnnexCheck(String annexNo);

    /**
     * 多值检索
     * 
     * @param key
     * @return
     */
    List<EscortAnnexCheck> queryAnnexChecks(Key key);

    @Data
    public static class Key {
        /**
         * 就诊卡号
         */
        private String cardNo = null;

        /**
         * 检测开始时间
         */
        private LocalDateTime inspectBeginDate = null;

        /**
         * 检测结束时间
         */
        private LocalDateTime inspectEndDate = null;
    }

    /**
     * 添加一条附件审核记录
     * 
     * @param escortAnnexChk 审核记录实体
     * @return
     */
    Integer insertAnnexCheck(EscortAnnexCheck escortAnnexChk);

    /**
     * 更新一条附件审核记录
     * 
     * @param escortAnnexChk 审核记录实体
     * @return
     */
    Integer updateAnnexCheck(EscortAnnexCheck escortAnnexChk);
}
