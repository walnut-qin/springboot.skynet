package com.kaos.skynet.api.controller.impl.inpatient.escort;

import com.kaos.skynet.core.Locks;
import com.kaos.skynet.core.type.thread.lock.Lock;

import org.springframework.core.convert.converter.Converter;

public final class LockMgr {
    /**
     * 患者卡号锁
     */
    public static Lock<String> patientLock = Locks.newLock("escortPatientLock", 20,
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
    public static Lock<String> helperLock = Locks.newLock("escortHelperLock", 20,
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
    public static Lock<String> stateLock = Locks.newLock("escortStateLock", 20,
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
    public static Lock<String> actionLock = Locks.newLock("escortActionLock", 20,
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
    public static Lock<String> annexLock = Locks.newLock("escortAnnexLock", 20,
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
