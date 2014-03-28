package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 本体的定义
 */
public class Block extends SubDevice {

    public Block() {
        setName("本体");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("上层油温");
        inspectContent.add("检查变压器的油位、油色");
        inspectContent.add("变压器本体、附件及各连接处无渗漏油");
        inspectContent.add("检查变压器本体及调压瓦斯继电器");
        inspectContent.add("运行中的声音");
        inspectContent.add("压力释放装置");
        inspectContent.add("充氮装置");
        inspectContent.add("呼吸器");
    }

    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.记录变压器上层油温数值（  ）℃,上层油温限值85℃,温升限值:45℃，绕组温度（  ）℃\n" +
                "2.变压器本体温度计完好,无破损。温包防雨罩完好\n" +
                "3.主控室远方测温数值正确,与主变本体温度指示数值相符,将变压器各部位所装温度计的指示互相对照、比较,防止误判断\n" +
                "4.相同运行条件下,上层油温比平时高10℃及以上,或负荷不变但油温不断上升,均为异常");
        inspectStandard.add("1.变压器的油位指示,应和油枕上的环境温度标志线相对应,无大偏差.指针式油位计指示,应与制造厂规定的温度曲线相对应。环境温度（  ）℃，油位指示（  ）\n" +
                "2.正常油色应为透明的淡黄色\n" +
                "3.油位计应无破损和渗漏油,没有影响察看油位的油垢");
        inspectStandard.add("1.检查有无渗漏油,要记录清楚渗漏的部位、程度\n" +
                "2.设备本体附着有油、灰的部位,必要时进行清擦;可利用多次巡检机会检查现象,鉴别是否渗油缺陷\n" +
                "3.渗漏油的部位,1min超过1滴,属于漏油");
        inspectStandard.add("1.瓦斯继电器内应充满油,油色应为淡黄色透明,无渗漏油.瓦斯继电器内应无气体(气泡)\n" +
                "2.瓦斯继电器防雨措施完好,防雨罩牢固\n" +
                "3.瓦斯继电器的引出二次电缆应无油迹和腐蚀现象,无松脱");
        inspectStandard.add("变压器正常运行应为均匀的嗡嗡声,无放电等异响，如声音不均匀,应使用听音棒区分是外部因素干扰或内部问题。若不能排除外部因素,应向上级汇报");
        inspectStandard.add("压力释放器有无油迹,二次电缆及护管无破损或腐蚀");
        inspectStandard.add("1.记录低压充氮装置气压（ ）MPa，正常时气压不得低于*******MPa\n" +
                "2.充氮装置各阀门正常开启");
        inspectStandard.add("1.硅胶颜色无受潮变色，如硅胶变为粉红色,则变色部分不得超过总量的2/3\n" +
                "2.呼吸器外部无油迹，油杯完好,油位正常");

    }
}
