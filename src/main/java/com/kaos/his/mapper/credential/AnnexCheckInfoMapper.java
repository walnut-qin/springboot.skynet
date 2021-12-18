package com.kaos.his.mapper.credential;

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
     * 获取距今最近（检测时间而非审核时间）的一次核酸检测审核结果
     * 
     * @param cardNo
     * @return
     */
    public AnnexCheckInfo QueryLatestExecInfo(String cardNo);

    /**
     * 插入一条审核结果
     * 
     * @param annexCheckInfo
     * @return
     */
    public int InsertAnnexCheckInfo(AnnexCheckInfo annexCheckInfo);
}
