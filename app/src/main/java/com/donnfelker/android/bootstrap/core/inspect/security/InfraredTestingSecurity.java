package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class InfraredTestingSecurity extends Security {

    public static String danger[] = {
        "擅自打开设备网门及移动临时安全围栏，跨越设备固定围栏",
        "高压设备发生接地时，保持距离不够，造成人员伤害",
        "人员碰撞建筑物或设备",
        "损坏红外线测试仪",
        "工作负责人选派不当,导致安全事故发生,检测质量无法保障。工作负责人和工作班员精神状态良好,工作前4 个小时内喝酒。",
    };

    public static String measure[] = {
        "巡检检查时不得进行其他工作（严禁进行电气工作），不得移开或越过遮拦",
        "高压设备发生接地时，室内不得接近故障点4 m以内，室外室内不得靠近故障点8 m以内，进入上述范围人员必须穿绝缘靴，接触设备的外壳和架构时，必须带绝缘手套",
        "佩戴安全防护用品，携带灯具。",
        "正确使用红外线测试仪，小心摔落或撞伤",
        "1. 红外检测人员必须掌握安全规程知识, 并经年度《电业安全工作规程》考试合格者。2. 红外检测人员经职业技能鉴定合格，持证上岗。3．实习人员必须经过安全教育后,方可在指导下参加指定的工作。4．红外检测人员应具备必要的电气技术理论知识，掌握相关设备、仪器的正确操作和保管方法,并掌握作业指导书中的各项要求。5．红外检测人员应掌握触电急救法和人工呼吸法等紧急救护法。6．患有不适宜电气试验工作的病症者，不得参与红外现场检测工作。"
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  5;}
}
