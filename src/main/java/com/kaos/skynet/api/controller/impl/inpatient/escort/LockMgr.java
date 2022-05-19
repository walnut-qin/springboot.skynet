package com.kaos.skynet.api.controller.impl.inpatient.escort;

import com.kaos.skynet.core.lock.Lock;
import com.kaos.skynet.core.lock.Locks;

import org.springframework.core.convert.converter.Converter;

public final class LockMgr {
    /**
     * 患者卡号锁
     */
    public static Lock<String> patientLock = Locks.newLockHelper("escortPatientLock", 20,
            new Converter<String, Integer>() {
                @Override
                public Integer convert(String source) {
                    // 键值为患者卡号，其值含字母，但直接转超过了Integer的最大值
                    Long longKey = Long.valueOf(source.replace("[^0-9]", ""));

                    // 取余
                    return Long.valueOf(longKey % 20).intValue();
                };
            });

    /**
     * 患者卡号锁
     */
    public static Lock<String> helperLock = Locks.newLockHelper("escortHelperLock", 20,
            new Converter<String, Integer>() {
                @Override
                public Integer convert(String source) {
                    // 键值为患者卡号，其值含字母，但直接转超过了Integer的最大值
                    Long longKey = Long.valueOf(source.replace("[^0-9]", ""));

                    // 取余
                    return Long.valueOf(longKey % 20).intValue();
                };
            });

    /**
     * 患者卡号锁
     */
    public static Lock<String> stateLock = Locks.newLockHelper("escortStateLock", 20,
            new Converter<String, Integer>() {
                @Override
                public Integer convert(String source) {
                    // 键值为患者卡号，其值含字母，但直接转超过了Integer的最大值
                    Long longKey = Long.valueOf(source.replace("[^0-9]", ""));

                    // 取余
                    return Long.valueOf(longKey % 20).intValue();
                };
            });

    /**
     * 患者卡号锁
     */
    public static Lock<String> actionLock = Locks.newLockHelper("escortActionLock", 20,
            new Converter<String, Integer>() {
                @Override
                public Integer convert(String source) {
                    // 键值为患者卡号，其值含字母，但直接转超过了Integer的最大值
                    Long longKey = Long.valueOf(source.replace("[^0-9]", ""));

                    // 取余
                    return Long.valueOf(longKey % 20).intValue();
                };
            });

    /**
     * 附件号锁
     */
    public static Lock<String> annexLock = Locks.newLockHelper("escortAnnexLock", 20,
            new Converter<String, Integer>() {
                @Override
                public Integer convert(String source) {
                    // 键值为患者卡号，其值含字母，但直接转超过了Integer的最大值
                    Long longKey = Long.valueOf(source.replace("[^0-9]", ""));

                    // 取余
                    return Long.valueOf(longKey % 20).intValue();
                };
            });
}
