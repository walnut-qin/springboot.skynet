package com.kaos.skynet.api.logic.controller.inpatient.escort;

import com.kaos.skynet.core.Locks;
import com.kaos.skynet.core.thread.lock.LockFactory;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortLock {
    /**
     * 患者卡号锁
     */
    final LockFactory patientLock = Locks.newLockFactory("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    final LockFactory helperLock = Locks.newLockFactory("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    final LockFactory stateLock = Locks.newLockFactory("escortStateLock", 20);

    /**
     * 患者卡号锁
     */
    final LockFactory actionLock = Locks.newLockFactory("escortActionLock", 20);

    /**
     * 附件号锁
     */
    final LockFactory annexLock = Locks.newLockFactory("escortAnnexLock", 20);
}
