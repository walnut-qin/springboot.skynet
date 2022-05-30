package com.kaos.skynet.api.controller.impl.inpatient.escort;

import com.kaos.skynet.core.Locks;
import com.kaos.skynet.core.thread.lock.LockFactory;

public final class LockMgr {
    /**
     * 患者卡号锁
     */
    public static LockFactory patientLock = Locks.newLockFactory("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockFactory helperLock = Locks.newLockFactory("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockFactory stateLock = Locks.newLockFactory("escortStateLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockFactory actionLock = Locks.newLockFactory("escortActionLock", 20);

    /**
     * 附件号锁
     */
    public static LockFactory annexLock = Locks.newLockFactory("escortAnnexLock", 20);
}
