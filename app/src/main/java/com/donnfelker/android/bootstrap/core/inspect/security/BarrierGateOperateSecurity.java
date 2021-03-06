package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class BarrierGateOperateSecurity extends Security {

    public static String danger[] = {
        "误接调度命令",
        "错误分解调度命令",
        "监控机上操作混乱",
        "操作票错误导致误操作",
        "操作中失去监护导致误操作",
        "任意解锁导致误操作",
    };

    public static String measure[] = {
        "接受调度命令应同时作好记录，并根据记录重复命令。重听命令录音，确认操作任务、操作注意事项及是否立即执行。在正班转发命令时，副班应在旁监听。",
        "正班应根据实际运行方式及调度命令的顺序分解调度命令并做好相应的记录，严禁擅自更改调度命令的顺序，副班应明确整个分解命令的过程及顺序。",
        "在监控机上的操作首先应有“将监控机画面切至XX间隔”这一步骤，确认画面正确后再进行操作。",
        "监护人、值班负责人严格审查操作票，操作中若发现操作票有错误，应立即停止操作，重新审核并填写正确的操作票后再行操作，禁止凑合使用错票跳步操作或凭经验操作。",
        "监护人必须履行监护的责任，在任一操作中，均不得有失去监护的操作发生。",
        "在操作中若遇锁具问题或微机防误程序确实有误时，应在监护人、操作人均确认需要解锁后，向总工如实汇报情况，得到解锁许可后，方可进行解锁操作。操作完成后必须做好记录。",
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  6;}
}
