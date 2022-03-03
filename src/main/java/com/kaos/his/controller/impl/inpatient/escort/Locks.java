package com.kaos.his.controller.impl.inpatient.escort;

import com.kaos.his.util.LockHelpers;
import com.kaos.his.util.helper.LockHelper;

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
    public static LockHelper stateLock = LockHelpers.newLockHelper("escortHelperLock", 20);

    /**
     * 患者卡号锁
     */
    public static LockHelper actionLock = LockHelpers.newLockHelper("escortHelperLock", 20);
}
