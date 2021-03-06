
package com.donnfelker.android.bootstrap.core;

import com.donnfelker.android.bootstrap.util.Ln;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import static com.donnfelker.android.bootstrap.core.Constants.Http.HEADER_PARSE_APP_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Http.HEADER_PARSE_REST_API_KEY;
import static com.donnfelker.android.bootstrap.core.Constants.Http.PARSE_APP_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Http.PARSE_REST_API_KEY;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_CHECKINS;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_DEFECT;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_NEWS;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_USERS;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_WEATHER;
import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_WORKS;
/**
 * Bootstrap API service
 */
public class BootstrapService {

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     * <p/>
     * You can also configure GSON with different naming policies for your API.
     * Maybe your API is Rails API and all json values are lower case with an underscore,
     * like this "first_name" instead of "firstName".
     * You can configure GSON as such below.
     * <p/>
     *
     * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
     *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
     */
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;

    /**
     * PARAM_SUBSTATION
     * 变电站ID
     */
    private static final String PARAM_SUBSTATION = "substation";

    private static final String PARAM_DEVICEID = "deviceid";

    private static final String PARM_LONGITUDE = "longtitude";

    private static final String PARM_LATITUDE = "latitude";

    private static final String PARM_MAC = "mac";


    private static class UsersWrapper {
        private List<User> results;
    }

    private static class NewsWrapper {
        private List<News> results;
    }

    private static class CheckInWrapper {
        private List<CheckIn> results;
    }

    private static class WorkWrapper {
        private List<Work> plan;
    }

    private static class DefectWrapper {
        private List<Defect> bugs;
    }

    private static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link JsonParseException}
         *
         * @param cause
         */
        public JsonException(final JsonParseException cause) {
            super(cause.getMessage());
            initCause(cause);
        }
    }

    private final String apiKey;
    private final String username;
    private final String password;
    private UserAgentProvider userAgentProvider;

    /**
     * Create bootstrap service
     *
     * @param username
     * @param password
     */
    public BootstrapService(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.apiKey = null;
    }

    /**
     * Create bootstrap service
     *
     * @param userAgentProvider
     * @param apiKey
     */
    public BootstrapService(final String apiKey, final UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
        this.username = null;
        this.password = null;
        this.apiKey = apiKey;
    }

    /**
     * Execute request
     * 判断Http请求是否可执行
     * @param request
     * @return request
     * @throws IOException
     */
    protected HttpRequest execute(final HttpRequest request) throws IOException {
        if (!configure(request).ok())
            throw new IOException("Unexpected response code: " + request.code());
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        request.userAgent(userAgentProvider.get());

        if (isPostOrPut(request)) {
            // All PUT & POST requests to Parse.com api must be in JSON
            // https://www.parse.com/docs/rest#general-requests
            request.contentType(Constants.Http.CONTENT_TYPE_JSON);
        }

        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(final HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
                || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(final HttpRequest request) {

        // Required params for
        request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY);
        request.header(HEADER_PARSE_APP_ID, PARSE_APP_ID);

        /*
         * NOTE: This may be where you want to add a header for the api token that was saved when
         * you logged in. In the bootstrap sample this is where we are saving the session id as
         * the token. If you actually had received a token you'd take the "apiKey" (aka: token)
         * and add it to the header or form values before you make your requests.
         *
         * Add the user name and password to the request here if your service needs username or
         * password for each request. You can do this like this:
         * request.basic("myusername", "mypassword");
         */

        return request;
    }

    private <V> V fromJson(final HttpRequest request, final Class<V> target) throws IOException {
        final Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (final JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (final IOException ignored) {
                // Ignored
            }
        }
    }

    /**
     * 获得所有的工作单数据，所有的数据来自于服务器（现在是本地服务器192.168.1.106:8888）
     *
     * @return 非null但是有可能是空列表
     * @throws IOException
     */
    public List<Work> getWorks(String substationID) throws IOException {
        try {
            // TODO 传入subtation id
            final String query = String.format("?%s=%s", PARAM_SUBSTATION, substationID);
            final HttpRequest request = execute(HttpRequest.get(URL_WORKS + query));
            final WorkWrapper response = fromJson(request, WorkWrapper.class);
            if (response != null && response.plan != null) {
                return response.plan;
            }
            return Collections.emptyList();
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }


    /**
     * 获得设备的历史缺陷信息
     * @return bug
     * @throws IOException
     */
    public List<Defect> getDefect(String deviceID) throws IOException {
        try {
            // TODO 传入device id
            final String query = String.format("?%s=%s", PARAM_DEVICEID, deviceID);
            final HttpRequest request = execute(HttpRequest.get(URL_DEFECT + query));
            Ln.d("device bug: %s", URL_DEFECT + query);
            final DefectWrapper response = fromJson(request, DefectWrapper.class);
            if (response != null && response.bugs != null) {
                return response.bugs;
            }
            return Collections.emptyList();
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }


    public Forecast getWeather(String longitude, String latitude, String mac) throws IOException {
        try {
            final String query = String.format("?%s=%s&%s=%s&%s=%s",
                    PARM_LONGITUDE, longitude,
                    PARM_LATITUDE, latitude,
                    PARM_MAC, mac);
            final HttpRequest request = execute(HttpRequest.get(URL_WEATHER + query));
            final Forecast response = fromJson(request, Forecast.class);
            Ln.d("Weather: request %s", URL_WEATHER + query);
            if (response != null ) {
                return response;
            }
            // TODO 返回null是不是不太好？
            return null;
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }



    /**
     * Get all bootstrap Users that exist on Parse.com
     *
     * @return non-null but possibly empty list of bootstrap
     * @throws IOException
     */
    public List<User> getUsers() throws IOException {
        try {
            final HttpRequest request = execute(HttpRequest.get(URL_USERS));
            final UsersWrapper response = fromJson(request, UsersWrapper.class);
            if (response != null && response.results != null) {
                return response.results;
            }
            return Collections.emptyList();
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all bootstrap News that exists on Parse.com
     *
     * @return non-null but possibly empty list of bootstrap
     * @throws IOException
     */
    public List<News> getNews() throws IOException {
        try {
            final HttpRequest request = execute(HttpRequest.get(URL_NEWS));
            final NewsWrapper response = fromJson(request, NewsWrapper.class);
            if (response != null && response.results != null) {
                return response.results;
            }
            return Collections.emptyList();
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all bootstrap Checkins that exists on Parse.com
     *
     * @return non-null but possibly empty list of bootstrap
     * @throws IOException
     */
    public List<CheckIn> getCheckIns() throws IOException {
        try {
            final HttpRequest request = execute(HttpRequest.get(URL_CHECKINS));
            final CheckInWrapper response = fromJson(request, CheckInWrapper.class);
            if (response != null && response.results != null) {
                return response.results;
            }
            return Collections.emptyList();
        } catch (final HttpRequestException e) {
            throw e.getCause();
        }
    }

}
