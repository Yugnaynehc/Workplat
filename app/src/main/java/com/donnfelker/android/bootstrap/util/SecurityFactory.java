package com.donnfelker.android.bootstrap.util;

import com.donnfelker.android.bootstrap.core.Constants;
import com.donnfelker.android.bootstrap.core.inspect.security.BarrierGateOperateSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.BatteryPeriodicTestingSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.BugTraceSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.DevicePeriodicMaintanceSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.DevicePeriodicTestingRotationSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.EmergencyLightSwitchSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.FoggySecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.InfraredTestingSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.NightLightSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.NormalSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.Security;
import com.donnfelker.android.bootstrap.core.inspect.security.SnowySecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.SwitchCoolerSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.ThunderstormSecurity;
import com.donnfelker.android.bootstrap.core.inspect.security.WindySecurity;

/**
 * Created by feather on 14-4-15.
 */
public class SecurityFactory {


    static public Security get(String type) {
        switch (Constants.Substation.indexOf(type)) {
            //case 1:
                //str = "全面巡检"; icon = "fa-check-square"; break;
                //return new 全面巡检危险点()
            case 2:
                //str = "日常巡检"; icon = "fa-tag"; break;
                return new NormalSecurity();
            case 3:
                //str = "雷雨特殊巡检"; icon = "fa-bolt"; break;
                return new ThunderstormSecurity();
            case 4:
                //str = "雪天特殊巡检"; icon = "fa-spinner"; break;
                return new SnowySecurity();
            case 5:
                //str = "大雾特殊巡检"; icon = "fa-eye"; break;
                return new FoggySecurity();
            case 6:
                //str = "大风特殊巡检"; icon = "fa-cog"; break;
                return new WindySecurity();
            case 7:
                //str = "夜间熄灯特殊巡检"; icon = "fa-lightbulb-o"; break;
                return new NightLightSecurity();
            case 8:
                //str = "设备异常缺陷跟踪特殊巡检"; icon = "fa-bell"; break;
                return new BugTraceSecurity();
            case 9:
                //str = "红外线测试作业"; icon = "fa-arrow-up"; break;
                return new InfraredTestingSecurity();
            case 10:
                //str = "主变冷却器切换试验作业"; icon = "fa-exchange"; break;
                return new SwitchCoolerSecurity();
            case 11:
                //str = "事故照明切换作业"; icon = "fa-sun-o"; break;
                return new EmergencyLightSwitchSecurity();
            case 12:
                //str = "蓄电池定期测试作业"; icon = "fa-adjust"; break;
                return new BatteryPeriodicTestingSecurity();
            case 13:
                //str = "轮换作业"; icon = "fa-refresh"; break;
                return new DevicePeriodicTestingRotationSecurity();
            case 14:
                //str = "设备定期维护作业"; icon = "fa-wrench"; break;
                return new DevicePeriodicMaintanceSecurity();
            case 15:
                //str = "道闸操作作业"; icon = "fa-random"; break;
                return new BarrierGateOperateSecurity();
            default:
                return null;
        }
    }
}
