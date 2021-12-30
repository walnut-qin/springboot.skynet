package com.kaos.his.controller;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.credential.EscortCardAction;
import com.kaos.his.entity.credential.EscortCardState;
import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.service.EscortService;
import com.kaos.util.DateHelper;
import com.kaos.util.GsonHelper;
import com.kaos.util.ListHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class EscortController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 日志对象
     */
    private Logger logger = Logger.getLogger(EscortController.class.getName());

    /**
     * QueryEscortState的响应体
     */
    class QueryEscortState_RspBody {
        /**
         * 陪护人卡号
         */
        public String escortCardNo = null;

        /**
         * 患者卡号
         */
        public String patientCardNo = null;

        /**
         * 姓名
         */
        public Date regDate = null;

        /**
         * 状态
         */
        public EscortStateEnum state = null;
    }

    /**
     * QueryHelper的响应体
     */
    class QueryActiveHelper_RspBody {
        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 免费标识： 0 - 不免费 1 - 免费
         */
        public String freeFlag = null;

        /**
         * 状态列表
         */
        public List<EscortCardState> states = null;

        /**
         * 行为列表
         */
        public List<EscortCardAction> actions = null;
    }

    /**
     * QueryActivePatient响应体
     */
    class QueryActivePatient_RspBody {
        /**
         * 陪护证编号
         */
        public String escortNo = null;

        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 科室名称
         */
        public String deptName = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 患者住院号
         */
        public String patientNo = null;

        /**
         * 免费标识： 0 - 不免费 1 - 免费
         */
        public String freeFlag = null;

        /**
         * 状态列表
         */
        public List<EscortCardState> states = null;

        /**
         * 行为列表
         */
        public List<EscortCardAction> actions = null;
    }

    /**
     * QueryUncheckEscortAnnex响应体
     */
    class QueryUncheckEscortAnnex_RspBody {
        /**
         * 陪护人姓名
         */
        public String helperName = null;

        /**
         * 附件ID
         */
        public String annexNo = null;

        /**
         * 图片外链
         */
        public String picUrl = null;

        /**
         * 患者姓名列表
         */
        public List<String> patientNames = null;
    }

    /**
     * QueryUncheckEscortAnnex响应体
     */
    class QueryCheckEscortAnnex_RspBody {
        /**
         * 陪护人姓名
         */
        public String helperName = null;

        /**
         * 附件ID
         */
        public String annexNo = null;

        /**
         * 图片外链
         */
        public String picUrl = null;

        /**
         * 患者姓名列表
         */
        public List<String> patientNames = null;

        /**
         * 核酸结果
         */
        public Boolean negative = null;

        /**
         * 检测时间
         */
        public Date execDate = null;
    }

    /**
     * 陪护证号锁
     */
    private final List<Object> escortNoLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 患者卡号锁
     */
    private final List<Object> patientLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 陪护人卡号锁
     */
    private final List<Object> helperLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 附件ID锁
     */
    private final List<Object> annexNoLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 定时任务处理线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 12, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>());

    /**
     * 发令枪
     */
    private CountDownLatch countDownLatch = null;

    /**
     * 将字符串转为锁索引
     * 
     * @param orgString
     * @param lockSize
     * @return
     */
    private Integer TransferToIndex(String orgString, Integer lockSize) {
        // 剔除字符串中的非数字字符
        String intStr = orgString.replaceAll("[^0-9]", "");
        this.logger.info(String.format("剔除非数字字符，%s -> %s", orgString, intStr));

        // 转换出索引
        var idx = Long.valueOf(intStr) % lockSize;
        this.logger.info(String.format("计算索引，%s -> %d", intStr, idx));

        // 响应索引号
        return (int) idx;
    }

    /**
     * 添加新的陪护证
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "regEscort", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String RegEscort(@RequestParam("patientCardNo") String patientCardNo,
            @RequestParam("helperCardNo") String helperCardNo) {
        // 入参判断
        if (patientCardNo == null || patientCardNo.isEmpty()) {
            throw new InvalidParameterException("患者号不能为空");
        } else if (helperCardNo == null || helperCardNo.isEmpty()) {
            throw new InvalidParameterException("陪护号不能为空");
        }

        // 声明陪护实体
        EscortCard recEscortCard = null;

        // 加患者锁
        var patientIdx = Integer.valueOf(patientCardNo.substring(patientCardNo.length() - 2)) % patientLocks.size();
        var patientLock = this.patientLocks.get(patientIdx);
        synchronized (patientLock) {
            // 加陪护锁
            var helperIdx = Integer.valueOf(helperCardNo.substring(helperCardNo.length() - 2)) % helperLocks.size();
            var helperLock = this.patientLocks.get(helperIdx);
            synchronized (helperLock) {
                // 添加陪护
                recEscortCard = this.escortService.InsertEscort(patientCardNo, helperCardNo, "Default");
            }
        }

        return GsonHelper.ToJson(recEscortCard);
    }

    /**
     * 注册陪护证
     * 
     * @param patientCardNo 患者卡号
     * @param helperCardNo  陪护人卡号
     * @param operCode      操作员
     * @return 陪护证实体json字符串
     */
    @RequestMapping(value = "escort/register", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String RegEscort(@RequestParam("patientCardNo") @NotEmpty(message = "患者卡号不能为空") String patientCardNo,
            @RequestParam("helperCardNo") @NotEmpty(message = "陪护卡号不能为空") String helperCardNo,
            @RequestParam("operCode") @NotEmpty(message = "操作员不能为空") String operCode) {
        // 记录log
        this.logger.info(String.format("登记陪护证 - patientCardNo(%s), helperCardNo(%s), operCode(%s)", patientCardNo,
                helperCardNo, operCode));

        // 声明陪护实体
        EscortCard escortCard = null;

        // 加锁 - 患者卡号
        var patientLockIdx = this.TransferToIndex(patientCardNo, this.patientLocks.size());
        this.logger.info(String.format("加锁 - patientLock(%d)", patientLockIdx));
        synchronized (this.patientLocks.get(patientLockIdx)) {
            // 加锁 - 陪护卡号
            var helperLockIdx = this.TransferToIndex(helperCardNo, this.helperLocks.size());
            this.logger.info(String.format("加锁 - helperLock(%d)", helperLockIdx));
            synchronized (this.helperLocks.get(helperLockIdx)) {
                // 注册陪护
                escortCard = this.escortService.InsertEscort(patientCardNo, helperCardNo, operCode);
            }
            this.logger.info(String.format("解锁 - helperLock(%d)", helperLockIdx));
        }
        this.logger.info(String.format("解锁 - patientLock(%d)", patientLockIdx));

        // 记录log
        this.logger.info(String.format("登记陪护证成功(%s)", escortCard.escortNo));

        return GsonHelper.ToJson(escortCard);
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "updateEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void UpdateEscortState(@RequestParam("escortNo") String escortNo,
            @RequestParam("newState") EscortStateEnum newState) {
        // 入参检查
        if (escortNo == null || escortNo.isEmpty()) {
            throw new InvalidParameterException("陪护证号不能为空");
        }

        // 记录日志
        this.logger.info(String.format("开始更新陪护证 %s 的状态", escortNo));

        // 加陪护号锁
        var idx = Integer.valueOf(escortNo.substring(escortNo.length() - 2)) % escortNoLocks.size();
        var lock = this.escortNoLocks.get(idx);
        this.logger.info(String.format("计算陪护证号锁编号为 %d", idx));

        synchronized (lock) {
            // 加锁日志
            this.logger.info(String.format("加锁 %d", idx));

            // 执行更新服务
            try {
                this.escortService.UpdateEscortState(escortNo, newState, "Default");
            } catch (Exception e) {
                // 异常日志
                this.logger.info(String.format("更新异常，%s", e.getMessage()));
            }

            // 解锁日志
            this.logger.info(String.format("解锁 %d", idx));
        }
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "escort/updateState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void UpdateEscortState(@RequestParam("escortNo") @NotEmpty(message = "陪护证编号不能为空") String escortNo,
            @RequestParam("newState") @NotEmpty(message = "状态枚举不能为空") EscortStateEnum newState,
            @RequestParam("operCode") @NotEmpty(message = "操作员编码不能为空") String operCode) {
        // 记录日志
        this.logger.info(String.format("更新陪护证(%s)的状态为(%s)", escortNo, newState.getDescription()));

        // 加陪护号锁
        var lockIdx = this.TransferToIndex(escortNo, this.escortNoLocks.size());
        this.logger.info(String.format("加锁 - 陪护证锁(%d)", lockIdx));
        synchronized (this.escortNoLocks.get(lockIdx)) {
            // 执行更新服务
            this.escortService.UpdateEscortState(escortNo, newState, operCode);
        }
        this.logger.info(String.format("解锁 - 陪护证锁(%d)", lockIdx));
    }

    class MyTask implements Runnable {
        /**
         * 任务即将处理的陪护证编号
         */
        private String escortNo = null;

        /**
         * 日志对象
         */
        private Logger logger = Logger.getLogger(MyTask.class.getName());

        /**
         * 构造函数
         */
        MyTask(String escortNo) {
            this.escortNo = escortNo;
        }

        /**
         * 核心处理方法
         */
        @Override
        public void run() {
            // 记录日志
            this.logger.info(String.format("开始更新陪护证 %s 的状态", this.escortNo));

            // 加锁日志
            var lockIdx = TransferToIndex(escortNo, escortNoLocks.size());
            this.logger.info(String.format("加锁 - 陪护证号锁(%d)", lockIdx));
            synchronized (escortNoLocks.get(lockIdx)) {
                // 执行更新服务
                try {
                    escortService.RefreshEscortState(escortNo);
                } catch (Exception e) {
                    // 异常日志
                    this.logger.info(String.format("更新异常，%s", e.getMessage()));
                }
            }
            this.logger.info(String.format("解锁 - 陪护证号锁(%d)", lockIdx));

            // 发令枪计时自减
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * 定期自动更新陪护证状态
     */
    @Scheduled(initialDelay = 1 * 1000, fixedDelay = 10 * 60 * 1000)
    public void AutoUpdateEscortState() {
        // 获取日志工具
        var logger = Logger.getLogger(EscortController.class.getName());

        // 从数据库中查询所有活跃着的陪护证编号
        var escortCards = this.escortService.QueryAllRegisteredEscortNo();

        // 记录开始日志
        logger.info(String.format(">> 开始更新，总计 %d 条活跃陪护证", escortCards.size()));
        var beginDate = new Date();

        // 轮训刷新状态
        this.countDownLatch = new CountDownLatch(escortCards.size());
        for (EscortCard escortCard : escortCards) {
            try {
                this.threadPoolExecutor.execute(new MyTask(escortCard.escortNo));
            } catch (Exception e) {
                logger.error(String.format("xx 更新陪护证 %s 的状态时发生错误: %s", escortCard.escortNo, e.getMessage()));
            }
        }
        try {
            this.countDownLatch.await();
        } catch (Exception e) {
            logger.error(String.format("主线程等待发令枪出现错误: %s", e.getMessage()));
        }

        // 记录日志
        var endDate = new Date();
        logger.info(String.format("<< 实际更新 %d，耗时 %d ms", escortCards.size(), endDate.getTime() - beginDate.getTime()));
    }

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "queryEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryEscortState(@RequestParam("escortNo") String escortNo) {
        // 提取指定陪护证
        var escort = this.escortService.QueryEscort(escortNo);
        if (escort == null) {
            throw new RuntimeException("未查询到陪护证");
        }

        // 创建响应实体
        var rspBody = new QueryEscortState_RspBody();
        rspBody.escortCardNo = escort.helperCardNo;
        rspBody.patientCardNo = escort.patientCardNo;
        rspBody.regDate = escort.states.get(0).operDate;
        rspBody.state = ListHelper.GetLast(escort.states).state;

        return GsonHelper.ToJson(rspBody);
    }

    /**
     * 获取患者的陪护人信息
     * 
     * @param cardNo 患者就诊卡号
     * @return
     */
    @RequestMapping(value = "queryActiveEscortHelper", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryActiveHelper(@RequestParam("patientCardNo") String patientCardNo) {
        // 入参判断
        if (patientCardNo == null) {
            throw new RuntimeException("患者卡号不能为空");
        }

        // 调取服务
        var escorts = this.escortService.QueryPatientRegisteredEscorts(patientCardNo);

        // 循环赋值
        var rspBody = new ArrayList<QueryActiveHelper_RspBody>();
        for (EscortCard escortCard : escorts) {
            // 创建响应实体元素
            var helperInfo = new QueryActiveHelper_RspBody();
            helperInfo.escortNo = escortCard.escortNo;
            helperInfo.cardNo = escortCard.helper.cardNo;
            helperInfo.name = escortCard.helper.name;
            helperInfo.sex = escortCard.helper.sex;
            helperInfo.age = DateHelper.GetAgeDetail(escortCard.helper.birthday);
            helperInfo.freeFlag = escortCard.helperCardNo.equals(escortCard.escortVip.helperCardNo) ? "1" : "0";

            // 双列表
            helperInfo.states = escortCard.states;
            helperInfo.actions = escortCard.actions;

            // 添加响应实体元素
            rspBody.add(helperInfo);
        }

        return GsonHelper.ToJson(rspBody);
    }

    @RequestMapping(value = "queryActiveEscortPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryActivePatient(@RequestParam("helperCardNo") String helperCardNo) {
        // 入参判断
        if (helperCardNo == null) {
            throw new RuntimeException("陪护人卡号不能为空");
        }

        // 调取服务
        var escorts = this.escortService.QueryHelperRegisteredEscorts(helperCardNo);

        // 循环赋值
        var rspBody = new ArrayList<QueryActivePatient_RspBody>();
        for (EscortCard escort : escorts) {
            // 创建新实体元素
            var patientInfo = new QueryActivePatient_RspBody();
            patientInfo.escortNo = escort.escortNo;
            patientInfo.cardNo = escort.preinCard.patient.cardNo;
            patientInfo.name = escort.preinCard.patient.name;
            patientInfo.sex = escort.preinCard.patient.sex;
            patientInfo.age = DateHelper.GetAgeDetail(escort.preinCard.patient.birthday);
            if (escort.preinCard.patient instanceof Inpatient) {
                patientInfo.deptName = ((Inpatient) escort.preinCard.patient).dept.name;
                patientInfo.bedNo = ((Inpatient) escort.preinCard.patient).bedNo;
                patientInfo.patientNo = ((Inpatient) escort.preinCard.patient).patientNo;
            } else {
                patientInfo.deptName = escort.preinCard.preDept.name;
                patientInfo.bedNo = escort.preinCard.preBedNo;
                patientInfo.patientNo = null;
            }
            patientInfo.freeFlag = escort.helperCardNo.equals(escort.escortVip.helperCardNo) ? "1" : "0";

            // 双列表
            patientInfo.states = escort.states;
            patientInfo.actions = escort.actions;

            // 添加实体元素
            rspBody.add(patientInfo);
        }

        return GsonHelper.ToJson(rspBody);
    }

    /**
     * 为陪护人添加院外陪护证附件
     * 
     * @param cardNo 患者就诊卡号
     * @return
     */
    @RequestMapping(value = "attachAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void AttachAnnex(@RequestParam("helperCardNo") String helperCardNo, @RequestParam("picUrl") String picUrl) {
        // 入参检查
        if (helperCardNo == null || helperCardNo.isEmpty()) {
            throw new InvalidParameterException("陪护人卡号不能为空");
        }

        // 执行业务
        this.escortService.AttachAnnex(helperCardNo, picUrl);
    }

    /**
     * 查询指定科室关联的未审核的陪护人附件
     * 
     * @param deptCode
     * @return
     */
    @RequestMapping(value = "queryUncheckEscortAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryUncheckEscortAnnex(@RequestParam("deptCode") String deptCode) {
        // 入参检查
        if (deptCode == null) {
            throw new InvalidParameterException("科室编码不能为空");
        }

        // 查询所有关联结果
        var rs = this.escortService.QueryAnnexInfos(deptCode, false);

        // 创建结果集
        var rspBody = new ArrayList<QueryUncheckEscortAnnex_RspBody>();

        // 提取结果集
        for (var rsItem : rs) {
            var item = new QueryUncheckEscortAnnex_RspBody();
            item.helperName = rsItem.getValue0().name;
            item.annexNo = rsItem.getValue1().annexNo;
            item.picUrl = rsItem.getValue1().annexUrl;
            item.patientNames = new ArrayList<String>();
            for (var patientName : rsItem.getValue2()) {
                item.patientNames.add(patientName.name);
            }
            rspBody.add(item);
        }

        return GsonHelper.ToJson(rspBody);
    }

    /**
     * 查询指定科室关联的未审核的陪护人附件
     * 
     * @param deptCode
     * @return
     */
    @RequestMapping(value = "queryCheckEscortAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryCheckEscortAnnex(@RequestParam("deptCode") String deptCode) {
        // 入参检查
        if (deptCode == null) {
            throw new InvalidParameterException("科室编码不能为空");
        }

        // 查询所有关联结果
        var rs = this.escortService.QueryAnnexInfos(deptCode, true);

        // 创建结果集
        var rspBody = new ArrayList<QueryCheckEscortAnnex_RspBody>();

        // 提取结果集
        for (var rsItem : rs) {
            var item = new QueryCheckEscortAnnex_RspBody();
            item.helperName = rsItem.getValue0().name;
            item.annexNo = rsItem.getValue1().annexNo;
            item.picUrl = rsItem.getValue1().annexUrl;
            item.patientNames = new ArrayList<String>();
            for (var patientName : rsItem.getValue2()) {
                item.patientNames.add(patientName.name);
            }
            item.negative = rsItem.getValue3().negative;
            item.execDate = rsItem.getValue3().execDate;
            rspBody.add(item);
        }

        return GsonHelper.ToJson(rspBody);
    }

    /**
     * 审核附件
     * 
     * @param annexNo
     * @param operCode
     * @param negative
     * @param execDate
     */
    @RequestMapping(value = "CheckEscortAnnex", method = RequestMethod.GET)
    public void CheckEscortAnnex(@NotEmpty(message = "附件编号不能为空") @RequestParam("annexNo") String annexNo,
            @NotEmpty(message = "操作员不能为空") @RequestParam("operCode") String operCode,
            @NotNull(message = "审核结果不能为空") @RequestParam("negative") Boolean negative,
            @NotNull(message = "核酸检测日期不能为空") @RequestParam("execDate") Date execDate) {
        // 加附件号锁
        var idx = Integer.valueOf(annexNo.substring(annexNo.length() - 2)) % annexNoLocks.size();
        var lock = this.annexNoLocks.get(idx);
        synchronized (lock) {
            // 执行更新服务
            this.escortService.CheckAnnexInfo(annexNo, operCode, negative, execDate);
        }
    }
}
