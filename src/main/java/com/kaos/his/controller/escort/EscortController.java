package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.TransTypeEnum;
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
     * 陪护证号锁
     */
    private List<Object> escortNoLocks = new ArrayList<>() {
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
     * 患者卡号锁
     */
    private List<Object> patientLocks = new ArrayList<>() {
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
    private List<Object> helperLocks = new ArrayList<>() {
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
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 12, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>());

    /**
     * 发令枪
     */
    private CountDownLatch countDownLatch = null;

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
                recEscortCard = this.escortService.InsertEscort(patientCardNo, helperCardNo);
            }
        }

        return GsonHelper.ToJson(recEscortCard);
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

        // 加陪护号锁
        var idx = Integer.valueOf(escortNo.substring(escortNo.length() - 2)) % escortNoLocks.size();
        var lock = this.escortNoLocks.get(idx);
        synchronized (lock) {
            // 执行更新服务
            this.escortService.UpdateEscortState(escortNo, newState);
        }
    }

    class MyTask implements Runnable {
        /**
         * 任务即将处理的陪护证编号
         */
        private String escortNo = null;

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
            // 加陪护号锁
            var idx = Integer.valueOf(escortNo.substring(escortNo.length() - 2)) % escortNoLocks.size();
            var lock = escortNoLocks.get(idx);
            synchronized (lock) {
                // 执行更新服务
                escortService.RefreshEscortState(escortNo);
            }

            // 发令枪计时自减
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * 定期自动更新陪护证状态
     */
    @Scheduled(initialDelay = 50 * 60 * 1000, fixedDelay = 10 * 60 * 1000)
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
        var rs = this.escortService.QueryUncheckedAnnexInfo(deptCode);

        // 创建结果集
        var rspBody = new ArrayList<QueryUncheckEscortAnnex_RspBody>();

        // 提取结果集
        for (var rsItem : rs.values()) {
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

    @RequestMapping(value = "CheckEscortAnnex", method = RequestMethod.GET)
    public void CheckEscortAnnex(@RequestParam("annexNo") TransTypeEnum annexNo) {
    }
}
