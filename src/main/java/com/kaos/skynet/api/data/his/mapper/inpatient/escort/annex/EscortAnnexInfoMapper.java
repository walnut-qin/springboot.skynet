package com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.annex.EscortAnnexInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface EscortAnnexInfoMapper {
    /**
     * 查询上传的附件实体
     * 
     * @param annexNo 附件号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortAnnexInfo queryAnnexInfo(String annexNo);

    /**
     * 查询符合条件的附件记录
     * 
     * @param key
     * @return 符合条件的附件记录（按照上传时间排序）
     */
    List<EscortAnnexInfo> queryAnnexInfos(Key key);

    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 就诊卡号, 等于 {@code null} 时，不作为判断条件
         */
        private String cardNo;

        /**
         * 上传时间起点, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime beginUploadDate;

        /**
         * 上传时间终点, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime endUploadDate;

        /**
         * 是否已审核
         */
        private Boolean checked;
    }

    /**
     * 插入一条附件记录
     * 
     * @param escortAnnexInfo 附件实体；不必设置 {@code annexNo}，执行语句时自动生成
     * @return
     */
    Integer insertAnnexInfo(EscortAnnexInfo escortAnnexInfo);
}
