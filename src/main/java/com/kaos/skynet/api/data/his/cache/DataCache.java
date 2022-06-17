package com.kaos.skynet.api.data.his.cache;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.his.cache.common.DawnOrgEmplCache;
import com.kaos.skynet.api.data.his.cache.common.MetOpsnWyDocCache;
import com.kaos.skynet.api.data.his.cache.common.config.ConfigMapCache;
import com.kaos.skynet.api.data.his.cache.common.config.ConfigMultiMapCache;
import com.kaos.skynet.api.data.his.cache.common.config.ConfigSwitchCache;
import com.kaos.skynet.api.data.his.cache.common.fee.FinComFeeCodeStatCache;
import com.kaos.skynet.api.data.his.cache.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinIprPrepayInCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinSpecialCityPatientCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortMainInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortStateRecCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortVipCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.annex.EscortAnnexInfoCache;
import com.kaos.skynet.api.data.his.cache.outpatient.FinOprRegisterCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * 缓存注册表
 */
@Getter
@Component
public class DataCache {
    @Autowired
    ConfigMapCache configMapCache;

    @Autowired
    ConfigMultiMapCache configMultiMapCache;

    @Autowired
    ConfigSwitchCache configSwitchCache;

    @Autowired
    FinComFeeCodeStatCache feeCodeStatCache;

    @Autowired
    ComPatientInfoCache patientInfoCache;

    @Autowired
    DawnOrgDeptCache deptCache;

    @Autowired
    DawnOrgEmplCache emplCache;

    @Autowired
    MetOpsnWyDocCache wyDocCache;

    @Autowired
    EscortAnnexCheckCache annexCheckCache;

    @Autowired
    EscortAnnexInfoCache annexInfoCache;

    @Autowired
    EscortMainInfoCache escortMainInfoCache;

    @Autowired
    EscortStateRecCache escortStateRecCache;

    @Autowired
    EscortVipCache escortVipCache;

    @Autowired
    ComBedInfoCache bedInfoCache;

    @Autowired
    FinIprInMainInfoCache inMainInfoCache;
    
    @Autowired
    FinIprPrepayInCache prepayInCache;

    @Autowired
    FinSpecialCityPatientCache specialCityPatientCache;

    @Autowired
    FinOprRegisterCache registerCache;

    public Map<String, Object> showCacheLog() {
        // 注册缓存实体
        Map<String, Object> caches = Maps.newHashMap();
        caches.put("configMapCache", configMapCache);
        caches.put("configMultiMapCache", Maps.newHashMap(new HashMap<String, Object>() {
            {
                put("masterCache", configMultiMapCache.getMasterCache());
                put("slaveCache", configMultiMapCache.getSlaveCache());
            }
        }));
        caches.put("configSwitchCache", configSwitchCache);
        caches.put("feeCodeStatCache", feeCodeStatCache);
        caches.put("patientInfoCache", patientInfoCache);
        caches.put("deptCache", deptCache);
        caches.put("emplCache", emplCache);
        caches.put("wyDocCache", wyDocCache);
        caches.put("annexCheckCache", annexCheckCache);
        caches.put("annexInfoCache", annexInfoCache);
        caches.put("escortMainInfoCache", escortMainInfoCache);
        caches.put("escortStateRecCache", escortStateRecCache);
        caches.put("escortVipCache", escortVipCache);
        caches.put("bedInfoCache", bedInfoCache);
        caches.put("inMainInfoCache", inMainInfoCache);
        caches.put("prepayInCache", prepayInCache);
        caches.put("specialCityPatientCache", specialCityPatientCache);
        caches.put("registerCache", registerCache);
        return caches;
    }
}
