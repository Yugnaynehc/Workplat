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
            case 1:
                return new NormalSecurity();
            case 2:
                return new NormalSecurity();
            case 3:
                return new ThunderstormSecurity();
            case 4:
                return new SnowySecurity();
            case 5:
                return new FoggySecurity();
            case 6:
                return new WindySecurity();
            case 7:
                return new NightLightSecurity();
            case 8:
                return new BugTraceSecurity();
            case 9:
                return new InfraredTestingSecurity();
            case 10:
                return new SwitchCoolerSecurity();
            case 11:
                return new EmergencyLightSwitchSecurity();
            case 12:
                return new BatteryPeriodicTestingSecurity();
            case 13:
                return new DevicePeriodicTestingRotationSecurity();
            case 14:
                return new DevicePeriodicMaintanceSecurity();
            case 15:
                return new BarrierGateOperateSecurity();
            default:
                return null;
        }
    }
}
