package com.shuailee.utils;

import com.shuailee.exception.exceptiontype.SysException;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class OKHttpClientUtils {

    private static final OkHttpClient client = new OkHttpClient();

    public static final MediaType APPLICATION_JSON = MediaType.get("application/json; charset=utf-8");

    private OKHttpClientUtils() {
    }

    public static String get(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String get(String url, Map<String, String> params) {
        Request request = new Request.Builder().url(url + "?" + map2String(params)).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String get(String url, Headers headers) {
        Request request = new Request.Builder().url(url).headers(headers).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String post(String url, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String post(String url, Headers headers, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder().url(url).headers(headers).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String delete(String url, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String delete(String url, Headers headers, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder().url(url).headers(headers).delete(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }


    public static String put(String url, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }


    public static String put(String url, Headers headers, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder().url(url).headers(headers).put(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String patch(String url, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    public static String patch(String url, Headers headers, String json) {
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request request = new Request.Builder().url(url).headers(headers).patch(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new SysException(e);
        }
    }


    private static String map2String(Map<String, String> map) {
        List<String> fields = new ArrayList<>();
        map.forEach((k, v) -> fields.add(k + "=" + v + "&"));
        int size = fields.size();
        String[] arrayToSort = fields.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        return sb.toString();
    }

}
