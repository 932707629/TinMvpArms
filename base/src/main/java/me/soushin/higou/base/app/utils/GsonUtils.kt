package me.soushin.higou.base.app.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

/**
 * Gson工具类
 * @auther SouShin
 * @time 2019/5/6 9:43
 */
object GsonUtils {
    private var gson: Gson?=null

    fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson as Gson
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    fun toString(x: Any): String {
        return getGson().toJson(x)
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> toBean(gsonString: String, cls: Class<T>): T {

        return getGson().fromJson(gsonString, cls)
    }

    /**
     * 转成list
     * 此方法不可用
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     * public static <T> List<T> toList(String gsonString, Class<T> cls) {
     * return getGson().fromJson(gsonString, new TypeToken<List></List><T>>() {}.getType());
     * }
    </T></T></T></T> */

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> toList(json: String, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(getGson().fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> toListMaps(gsonString: String): List<Map<String, T>> {
        return getGson().fromJson(gsonString, object : TypeToken<List<Map<String, T>>>() {

        }.type)
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> toMaps(gsonString: String): Map<String, T> {
        return getGson().fromJson(gsonString, object : TypeToken<Map<String, T>>() {
        }.type)
    }


}
