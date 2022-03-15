package com.kaos.skynet.api.controller.impl.inpatient.escort;

import com.kaos.skynet.util.LockHelpers;
import com.kaos.skynet.util.helper.LockHelper;

public final class Locks {
    /**
     * 患者卡号锁
     */
    public static LockHelper patientLock = LockHelpers.newLockHelper("escortPatientLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockHelper helperLock = LockHelpers.newLockHelper("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockHelper stateLock = LockHelpers.newLockHelper("escortStateLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockHelper actionLock = LockHelpers.newLockHelper("escortActionLock", 20);

    /**
     * 附件号锁
     */
    public static LockHelper annexLock = LockHelpers.newLockHelper("escortAnnexLock", 20);
}
