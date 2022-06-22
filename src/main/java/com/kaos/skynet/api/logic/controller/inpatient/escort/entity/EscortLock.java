package com.kaos.skynet.api.logic.controller.inpatient.escort.entity;

import com.kaos.skynet.core.util.thread.lock.LockGuardian;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortLock {
    /**
     * 患者卡号锁
     */
    final LockGuardian patientLock = new LockGuardian("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    final LockGuardian helperLock = new LockGuardian("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    final LockGuardian stateLock = new LockGuardian("escortStateLock", 80);

    /**
     * 患者卡号锁
     */
    final LockGuardian actionLock = new LockGuardian("escortActionLock", 20);

    /**
     * 附件号锁
     */
    final LockGuardian annexLock = new LockGuardian("escortAnnexLock", 20);
}
