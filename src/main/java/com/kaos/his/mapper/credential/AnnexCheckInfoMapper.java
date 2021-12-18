package com.kaos.his.mapper.credential;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.credential.AnnexCheckInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface AnnexCheckInfoMapper {
    /**
     * 主键查询
     * 
     * @param annexNo
     * @return
     */
    public AnnexCheckInfo QueryAnnexCheckInfo(String annexNo);

    /**
     * 查询特定人员某个时段内的核酸检测结果
     * 
     * @param cardNo
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<AnnexCheckInfo> QueryWithExecDateLimit(String cardNo, Date beginDate, Date endDate);

    /**
     * 插入一条审核结果
     * 
     * @param annexCheckInfo
     * @return
     */
    public int InsertAnnexCheckInfo(AnnexCheckInfo annexCheckInfo);
}
