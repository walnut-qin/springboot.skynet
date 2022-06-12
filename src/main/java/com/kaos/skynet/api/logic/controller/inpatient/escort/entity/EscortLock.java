package com.kaos.skynet.api.logic.controller.inpatient.escort.entity;

import com.kaos.skynet.core.thread.Threads;
import com.kaos.skynet.core.thread.lock.LockFactory;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortLock {
    /**
     * 患者卡号锁
     */
    final LockFactory patientLock = Threads.newLockFactory("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    final LockFactory helperLock = Threads.newLockFactory("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    final LockFactory stateLock = Threads.newLockFactory("escortStateLock", 80);

    /**
     * 患者卡号锁
     */
    final LockFactory actionLock = Threads.newLockFactory("escortActionLock", 20);

    /**
     * 附件号锁
     */
    final LockFactory annexLock = Threads.newLockFactory("escortAnnexLock", 20);
}
