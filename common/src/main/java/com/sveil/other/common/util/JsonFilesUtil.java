package com.sveil.other.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * JsonFilesUtil json文件数据库操作工具
 * 
 * @author richard
 * 
 */
public class JsonFilesUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonFilesUtil.class);

    /**
     * 实例初始化
     * 
     * @param pathes
     * @return
     */
    public static String load(String pathFile) {
        return loadFiles(pathFile);
    }

    /**
     * 功能描述:读取json文件
     * 
     * @param pathFile
     * 
     * @return String
     */
    public static String loadFiles(String pathFile) {
        if (pathFile == null) {
            logger.error("读取指定文件不能为空");
            return null;
        }
        String txtStr = "";
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];
        BufferedReader br = null;
        try {
            File file = new File(pathFile);
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            while (true) {
                read = br.read(buffer, 0, N);
                txtStr += new String(buffer, 0, read);

                if (read < N) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("系统找不到指定文件:" + pathFile);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("打开文件" + pathFile + "出现错误");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                logger.error("读取文件" + pathFile + "后关闭文件出现错误");
                ex.printStackTrace();
            }
        }
        return txtStr;
    }

    /**
     * 功能描述:写入文件
     * 
     * @param pathFile
     * 
     * @return void
     */
    public static void exportFile(String pathFile, String newStr) {
        if (pathFile == null) {
            logger.error("写入指定文件不能为空");
            return;
        }
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(pathFile);
            bw = new BufferedWriter(fw);
            bw.write(newStr);
            bw.newLine();
        } catch (FileNotFoundException e) {
            logger.error("系统找不到指定文件:" + pathFile);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("写入文件" + pathFile + "出现错误");
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException ex) {
                logger.error("写入文件" + pathFile + "后关闭文件出现错误");
                ex.printStackTrace();
            }
        }
    }

    /**
     * 功能描述:添加json字段
     * 
     * @param String
     * @param JSONObject
     * 
     * @return JSONObject
     */
    public static JSONObject addJsonField(String fieldStr, JSONObject jsonObj) {
        if (fieldStr == null) {
            logger.error("添加的字段不能为空");
            return null;
        }
        try {
            JSONArray jsonArray = JSONArray.fromObject(jsonObj);
            jsonObj.put(fieldStr, jsonArray);
        } catch (JSONException e) {
            logger.error("添加字段" + fieldStr + "出现错误");
            e.printStackTrace();
        }
        return jsonObj;
    }

    /**
     * 功能描述:删除json字段
     * 
     * @param String
     * @param String
     * 
     * @return String
     */
    public static String removeJsonField(String fieldStr, String jsonStr) {
        if (fieldStr == null) {
            logger.error("删除的字段不能为空");
            return null;
        }
        JSONObject jsonObj = null;
        JSONArray jsonArr = null;
        try {
            jsonObj = JSONObject.fromObject(jsonStr);
            jsonArr = jsonObj.getJSONArray(fieldStr);
        } catch (JSONException e) {
            logger.error("删除字段" + fieldStr + "出现错误");
            e.printStackTrace();
        }
        return jsonArr.toString();
    }

    /**
     * 功能描述:添加记录到json
     * 
     * @param JSONObject
     * @param JSONObject
     * 
     * @return JSONObject
     */
    public static JSONObject addJsonRecord(JSONObject jsonObj, JSONObject jsonRecordObj) {
        if (jsonRecordObj == null) {
            logger.error("添加的记录不能为空");
            return null;
        }
        try {
            jsonObj.putAll(jsonRecordObj);
        } catch (JSONException e) {
            logger.error("添加记录" + jsonRecordObj + "出现错误");
            e.printStackTrace();
        }
        return jsonObj;
    }

    /**
     * 功能描述:生成json文件
     * 
     * @param String
     * 
     * @return void
     */
    public static void jsonEncode(String dbStr, String tableStr, JSONObject jsonObj) {
        jsonObj = addJsonField(tableStr, jsonObj);
        exportFile(dbStr, jsonObj.toString());
    }

    /**
     * 功能描述:解析出json记录集合
     * 
     * @param String
     * 
     * @return String
     */
    public static String jsonDecode(String[] args) {
        String text = null;
        return text;
    }

    /**
     * 功能描述:json转为简单Bean
     * 
     * @param String
     * 
     * @return <T> 泛型对象
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, cls);
        } catch (JSONException e) {
            logger.error("json转为简单Bean出现错误");
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 功能描述:json转为简单list
     * 
     * @param String
     * 
     * @return List<T> 泛型列表对象
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, cls);
        } catch (JSONException e) {
            logger.error("json转为简单list出现错误");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 功能描述:json转为list集合
     * 
     * @param String
     * 
     * @return List<T> 泛型列表对象
     */
    public static List<Map<String, Object>> jsonToListMaps(String jsonStr) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonStr, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (JSONException e) {
            logger.error("json转为list集合出现错误");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
     * 
     * @param bean
     *            bean对象
     * @return java.lang.String
     */
    public static String beanToJson(Object bean) throws Exception {
        String jsonStr = null;
        Gson gson = new Gson();
        jsonStr = gson.toJson(bean);
        return jsonStr;
    }

    /**
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
     * 
     * @param List
     *            列表对象
     * @return java.lang.String
     */
    public static String listToJson(List<?> list) throws Exception {
        String jsonStr = null;
        Gson gson = new Gson();
        jsonStr = gson.toJson(list);
        return jsonStr;
    }
}