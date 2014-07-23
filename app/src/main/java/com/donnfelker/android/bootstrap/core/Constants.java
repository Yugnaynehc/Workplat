

package com.donnfelker.android.bootstrap.core;

/**
 * Bootstrap constants
 */
public final class Constants {
    private Constants() {}

    public static final class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.androidbootstrap";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "Android Bootstrap";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.androidbootstrap.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Http {
        private Http() {}


        /**
         * Base URL for all requests
         */
        //public static final String URL_BASE = "https://api.parse.com";
        //public static final String URL_BASE = "http://192.168.1.106:8888/Inspect";
        public static final String URL_BASE = "http://124.16.136.235:8082/Inspect";
        //public static final String URL_BASE = "http://44b3f4b7.ngrok.com/Inspect";

        /**
         * Authentication URL
         */
        //public static final String URL_AUTH = URL_BASE + "/1/login";
        public static final String URL_AUTH = URL_BASE + "/loginAction";
        /**
         *  获取工作单列表的URL
         */
        public static final String URL_WORKS = URL_BASE + "/planAction";

        /**
         *  获取缺陷信息的URL
         */
        public static final String URL_DEFECT = URL_BASE + "/getDeviceBugAction";

        /**
         *  申请工作单的URL
         */
        public static final String URL_APPLY = URL_BASE + "/applySaveAction";

        /**
         *  上传结果的URL
         */
        public static final String URL_UPLOAD = URL_BASE + "/uploadPro";

        /**
         *  获得天气信息的URL
         */
        public static final String URL_WEATHER = URL_BASE + "/weatherAction";

        /**
         * List Users URL
         */
        public static final String URL_USERS = URL_BASE + "/1/users";

        /**
         * List News URL
         */
        public static final String URL_NEWS = URL_BASE + "/1/classes/News";

        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";

        public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
        public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


    }


    public static final class Extra {
        private Extra() {}

        public static final String NEWS_ITEM = "news_item";

        public static final String USER = "user";

        public static final String WORK = "work";

        public static final String WORK_ITEM = "work_item";

        public static final String WORK_ID = "work_id";

        public static final String DEVICE_ID = "device_id";

        public static final String DEVICE_NAME = "device_name";

        public static final String DEVICE_DATE = "device_date";

        public static final String DEVICE_CONTENT = "device_content";

        public static final String DEVICE_STANDARD = "device_standard";

        public static final String DEVICE_RESULT = "device_result";

        public static final String DEVICE_NO = "device_no";

        public static final String DEVICE_ITEM_NO = "device_item_no";

    }

    public static final class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.donnfelker.android.bootstrap.";

        public static final int ADD_NEW_RESULT = 1;

        public static final int EDIT_EXISTENT_RESULT = 2;

        public static final int FINISH_WORK = 3;

    }

    public static class Notification {
        private Notification() {
        }

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

    /**
     * 变电站有关信息的常量定义
     */
    public static class Substation {
        private Substation() {}

        /**
         * 巡检类型与巡检代码
         */
        // 全面巡检
        public static String INSPECT_NORMAL_TOTAL ="INSPECT_NORMAL_TOTAL";
        // 日常巡检
        public static String INSPECT_NORMAL_DAILY ="INSPECT_NORMAL_DAILY";
        // 雷雨特殊巡检
        public static String INSPECT_SPECIAL_THUNDERSTORM ="INSPECT_SPECIAL_THUNDERSTORM";
        // 雪天特殊巡检
        public static String INSPECT_SPECIAL_SNOWY ="INSPECT_SPECIAL_SNOWY";
        // 大雾特殊巡检
        public static String INSPECT_SPECIAL_FOGGY ="INSPECT_SPECIAL_FOGGY";
        // 大风特殊巡检
        public static String INSPECT_SPECIAL_WINDY ="INSPECT_SPECIAL_WINDY";
        // 夜间熄灯特殊巡检(数据库定义文档出现typo，把light写成了ligth)
        public static String INSPECT_SPECIAL_NIGHTLIGHT ="INSPECT_SPECIAL_NIGHTLIGTH";
        // 设备异常缺陷跟踪特殊巡检
        public static String INSPECT_SPECIAL_BUGTRACE ="INSPECT_SPECIAL_BUGTRACE";
        // 红外线测试作业
        public static String INSPECT_JOB_INFRAREDTESTING ="INSPECT_JOB_INFRAREDTESTING";
        // 主变冷却器切换试验作业
        public static String INSPECT_JOB_SWITCHCOOLER ="INSPECT_JOB_SWITCHCOOLER";
        // 事故照明切换作业
        public static String INSPECT_JOB_EMERGENCYLIGHTSWITCH ="INSPECT_JOB_EMERGENCYLIGHTSWITCH";
        // 蓄电池定期测试作业
        public static String INSPECT_JOB_BATTERYPERIODICTESTING ="INSPECT_JOB_BATTERYPERIODICTESTING";
        // 设备定期测试、轮换作业
        public static String INSPECT_JOB_DEVICEPERIODICTESTINGROTATION ="INSPECT_JOB_DEVICEPERIODICTESTINGROTATION";
        // 设备定期维护作业
        public static String INSPECT_JOB_DEVICEPERIODICMAINTANCE ="INSPECT_JOB_DEVICEPERIODICMAINTANCE";
        // 道闸操作作业
        public static String INSPECT_JOB_BARRIERGATEOPERATE ="INSPECT_JOB_BARRIERGATEOPERATE";

        public static String INSPECT_TYPE_LIST[] = {
                INSPECT_NORMAL_TOTAL,
                INSPECT_NORMAL_DAILY,
                INSPECT_SPECIAL_THUNDERSTORM,
                INSPECT_SPECIAL_SNOWY,
                INSPECT_SPECIAL_FOGGY,
                INSPECT_SPECIAL_WINDY,
                INSPECT_SPECIAL_NIGHTLIGHT,
                INSPECT_SPECIAL_BUGTRACE,
                INSPECT_JOB_INFRAREDTESTING,
                INSPECT_JOB_SWITCHCOOLER,
                INSPECT_JOB_EMERGENCYLIGHTSWITCH,
                INSPECT_JOB_BATTERYPERIODICTESTING,
                INSPECT_JOB_DEVICEPERIODICTESTINGROTATION,
                INSPECT_JOB_DEVICEPERIODICMAINTANCE,
                INSPECT_JOB_BARRIERGATEOPERATE
        };

        // 获得巡检类型代码（因为Java的switch不支持字符串类型，所以需要这个函数来完成转换）
        public static int indexOf(String str) {
            if (str.equals(INSPECT_NORMAL_TOTAL) || str.equals("全面巡检")) return 1;
            else if (str.equals(INSPECT_NORMAL_DAILY) || str.equals("日常巡检")) return 2;
            else if (str.equals(INSPECT_SPECIAL_THUNDERSTORM) || str.equals("雷雨特殊巡检")) return 3;
            else if (str.equals(INSPECT_SPECIAL_SNOWY) || str.equals("雪天特殊巡检")) return 4;
            else if (str.equals(INSPECT_SPECIAL_FOGGY) || str.equals("大雾特殊巡检")) return 5;
            else if (str.equals(INSPECT_SPECIAL_WINDY ) || str.equals("大风特殊巡检")) return 6;
            else if (str.equals(INSPECT_SPECIAL_NIGHTLIGHT) || str.equals("夜间熄灯特殊巡检")) return 7;
            else if (str.equals(INSPECT_SPECIAL_BUGTRACE) || str.equals("设备异常缺陷跟踪特殊巡检")) return 8;
            else if (str.equals(INSPECT_JOB_INFRAREDTESTING) || str.equals("红外线测试作业")) return 9;
            else if (str.equals(INSPECT_JOB_SWITCHCOOLER) || str.equals("主变冷却器切换试验作业")) return 10;
            else if (str.equals(INSPECT_JOB_EMERGENCYLIGHTSWITCH) || str.equals("事故照明切换作业")) return 11;
            else if (str.equals(INSPECT_JOB_BATTERYPERIODICTESTING) || str.equals("蓄电池定期测试作业")) return 12;
            else if (str.equals(INSPECT_JOB_DEVICEPERIODICTESTINGROTATION) || str.equals("轮换作业")) return 13;
            else if (str.equals(INSPECT_JOB_DEVICEPERIODICMAINTANCE) || str.equals("设备定期维护作业")) return 14;
            else if (str.equals(INSPECT_JOB_BARRIERGATEOPERATE) || str.equals("道闸操作作业")) return 15;
            else return 0;

        }
    }

    /**
     * Shared preferences for user information
     */
    public static class UPreference {

        public static final String USER_INFO = "user_info";
        public static final String USER_INFO_NAME = USER_INFO + "_name";
        public static final String USER_INFO_ID = USER_INFO + "_id";
        public static final String USER_INFO_SUBSTATION_ID = USER_INFO  + "_substationid";
    }

}
