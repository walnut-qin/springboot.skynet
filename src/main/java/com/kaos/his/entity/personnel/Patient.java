package com.kaos.his.entity.personnel;

import java.util.List;

import com.kaos.his.entity.lis.NucleicAcidTest;
import com.kaos.his.entity.order.OutpatientOrder;

public class Patient extends Citizen {
    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 是否为GCP门诊患者标识
     */
    public Boolean gcpOutPatient = null;

    /**
     * 核酸检测结果
     */
    public List<NucleicAcidTest> nucleicAcidTests = null;

    /**
     * 医嘱列表
     */
    public List<OutpatientOrder> outpatientOrders = null;
}
