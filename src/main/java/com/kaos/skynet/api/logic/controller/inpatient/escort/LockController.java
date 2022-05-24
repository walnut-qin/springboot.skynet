package com.kaos.skynet.api.logic.controller.inpatient.escort;

import com.kaos.skynet.core.Locks;
import com.kaos.skynet.core.type.thread.lock.LockFactory;

public interface AbstractController {
    /**
     * 患者卡号锁
     */
    final static LockFactory patientLock = Locks.newLockFactory("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    final static LockFactory helperLock = Locks.newLockFactory("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    final static LockFactory stateLock = Locks.newLockFactory("escortStateLock", 20);

    /**
     * 患者卡号锁
     */
    final static LockFactory actionLock = Locks.newLockFactory("escortActionLock", 20);

    /**
     * 附件号锁
     */
    final static LockFactory annexLock = Locks.newLockFactory("escortAnnexLock", 20);
}
