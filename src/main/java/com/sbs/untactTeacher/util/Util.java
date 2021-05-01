package com.sbs.untactTeacher.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {
    public static String getNowDateStr() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        return format1.format(time);
    }

    public static Map<String, Object> mapOf(Object... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("인자를 짝수개 입력해주세요.");
        }

        int size = args.length / 2;

        Map<String, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < size; i++) {
            int keyIndex = i * 2;
            int valueIndex = keyIndex + 1;

            String key;
            Object value;

            try {
                key = (String) args[keyIndex];
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("키는 String으로 입력해야 합니다. " + e.getMessage());
            }

            value = args[valueIndex];

            map.put(key, value);
        }

        return map;
    }

    public static int getAsInt(Object object, int defaultValue) {
        if (object instanceof BigInteger) {
            return ((BigInteger) object).intValue();
        } else if (object instanceof Double) {
            return (int) Math.floor((double) object);
        } else if (object instanceof Float) {
            return (int) Math.floor((float) object);
        } else if (object instanceof Long) {
            return (int) object;
        } else if (object instanceof Integer) {
            return (int) object;
        } else if (object instanceof String) {
            return Integer.parseInt((String) object);
        }

        return defaultValue;
    }

    public static String msgAndBack(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }

    public static String msgAndReplace(String msg, String uri) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.replace('" + uri + "');");
        sb.append("</script>");

        return sb.toString();
    }

    public static String toJsonStr(Map<String, Object> param) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> param = new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);

            param.put(paramName, paramValue);
        }

        return param;
    }

    public static String getUriEncoded(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static <T> T ifNull(T data, T defaultValue) {
        return data != null ? data : defaultValue;
    }

    public static <T> T reqAttr(HttpServletRequest req, String attrName, T defaultValue) {
        return (T) ifNull(req.getAttribute(attrName), defaultValue);
    }

    public static boolean isEmpty(Object data) {
        if (data == null) {
            return true;
        }

        if (data instanceof String) {
            String strData = (String) data;

            return strData.trim().length() == 0;
        } else if (data instanceof List) {
            List listData = (List) data;

            return listData.isEmpty();
        } else if (data instanceof Map) {
            Map mapData = (Map) data;

            return mapData.isEmpty();
        }

        return false;
    }

    public static <T> T ifEmpty(T data, T defaultValue) {
        if (isEmpty(data)) {
            return defaultValue;
        }

        return data;
    }

    public static String getFileExtTypeCodeFromFileName(String fileName) {
        String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
            case "gif":
            case "png":
                return "img";
            case "mp4":
            case "avi":
            case "mov":
                return "video";
            case "mp3":
                return "audio";
        }

        return "etc";
    }

    public static String getFileExtType2CodeFromFileName(String fileName) {
        String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
                return "jpg";
            case "gif":
                return ext;
            case "png":
                return ext;
            case "mp4":
                return ext;
            case "mov":
                return ext;
            case "avi":
                return ext;
            case "mp3":
                return ext;
        }

        return "etc";
    }

    public static String getFileExtFromFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);

        return ext;
    }

    public static String getNowYearMonthDateStr() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM");

        String dateStr = format1.format(System.currentTimeMillis());

        return dateStr;
    }

    public static List<Integer> getListDividedBy(String str, String divideBy) {
        return Arrays.asList(str.split(divideBy)).stream().map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    public static boolean delteFile(String filePath) {
        java.io.File ioFile = new java.io.File(filePath);
        if (ioFile.exists()) {
            return ioFile.delete();
        }

        return true;
    }

    public static String numberFormat(int num) {
        DecimalFormat df = new DecimalFormat("###,###,###");

        return df.format(num);
    }

    public static String numberFormat(String numStr) {
        return numberFormat(Integer.parseInt(numStr));
    }

    public static boolean allNumberString(String str) {
        if (str == null) {
            return false;
        }

        if (str.length() == 0) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }

        return true;
    }

    public static boolean startsWithNumberString(String str) {
        if (str == null) {
            return false;
        }

        if (str.length() == 0) {
            return false;
        }

        return Character.isDigit(str.charAt(0));
    }

    public static boolean isStandardLoginIdString(String str) {
        if (str == null) {
            return false;
        }

        if (str.length() == 0) {
            return false;
        }

        // 조건
        // 5자 이상, 20자 이하로 구성
        // 숫자로 시작 금지
        // _, 알파벳, 숫자로만 구성
        return Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,19}$", str);
    }

    public static String getNewUrlRemoved(String uri, String paramName) {
        String deleteStrStarts = paramName + "=";
        int delStartPos = uri.indexOf(deleteStrStarts);

        if (delStartPos != -1) {
            int delEndPos = uri.indexOf("&", delStartPos);

            if (delEndPos != -1) {
                delEndPos++;
                uri = uri.substring(0, delStartPos) + uri.substring(delEndPos, uri.length());
            } else {
                uri = uri.substring(0, delStartPos);
            }
        }

        if (uri.charAt(uri.length() - 1) == '?') {
            uri = uri.substring(0, uri.length() - 1);
        }

        if (uri.charAt(uri.length() - 1) == '&') {
            uri = uri.substring(0, uri.length() - 1);
        }

        return uri;
    }

    public static String getNewUri(String uri, String paramName, String paramValue) {
        uri = getNewUrlRemoved(uri, paramName);

        if (uri.contains("?")) {
            uri += "&" + paramName + "=" + paramValue;
        } else {
            uri += "?" + paramName + "=" + paramValue;
        }

        uri = uri.replace("?&", "?");

        return uri;
    }

    public static String getNewUriAndEncoded(String uri, String paramName, String pramValue) {
        return getUriEncoded(getNewUri(uri, paramName, pramValue));
    }

    public static String msgAndBack(HttpServletRequest req, String msg) {
        req.setAttribute("msg", msg);
        req.setAttribute("historyBack", true);
        return "common/redirect";
    }

    public static String msgAndReplace(HttpServletRequest req, String msg, String replaceUri) {
        req.setAttribute("msg", msg);
        req.setAttribute("replaceUri", replaceUri);
        return "common/redirect";
    }

    public static String getTempPassword(int length) {
        int index = 0;
        char[] charArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            index = (int) (charArr.length * Math.random());
            sb.append(charArr[index]);
        }

        return sb.toString();
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getDateStrLater(int seconds) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStr = format1.format(System.currentTimeMillis() + seconds * 1000);

        return dateStr;
    }
}
