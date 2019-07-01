package top.wmjgm.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;
import top.wmjgm.constants.Constants;
import top.wmjgm.response.TradeOrderResultData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *     小程序支付参数生成工具
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 00:52
 **/
public class WeChatPayUtil {

    /**
     * 获取签名 算法 MD5签名方式
     * @param param 请求支付接口的额所有参数
     * @return getSign
     */
    public static String getSign(Object param){

        String paramJson = JackSonUtil.entityToJson(param);
        Map<String, Object> map = JackSonUtil.jsonToMap(paramJson);
        if (map == null || map.size() <= 0) {
            return null;
        }
        return MD5Util.md5(createLinkString(map) +
                "&key=" + Constants.PARTNER_KEY);

    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        if (params == null || params.size() <= 0) {
            return null;
        }
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            /**
             * 拼接时，不包括最后一个&字符
             */
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 随机生成字符串
     * @return
     */
    public static String getRandomKey() {
        return getRandomKey(32);
    }

    /**
     * 随机生成字符串
     * @param length
     * @return
     */
    private static String getRandomKey(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }

        return stringBuffer.toString();
    }

    /**
     * 生成商户订单号
     * @return
     */
    public static String getOrderNumber(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        /**
         * 输出字符串
         */
        return df.format(new Date());
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static String getTimeStamp(){

        return String.valueOf(System.currentTimeMillis() / 1000);

    }

    /**
     * 获取当前时间
     * 格式：yyyyMMddHHmmss
     * @return
     */
    public static String getTimeStart(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        /**
         * 输出字符串
         */
        return dateFormat.format(new Date());
    }

    /**
     * 获取结束时间
     * @param timestamp 交易发起时间
     * @param amount 交易结束天数
     * @return
     */
    public static String getTimeExpire(String timestamp, int amount){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = dateFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("交易日期格式不正确，请将格式调整为yyyyMMddHHmmss");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 数据处理
     * @param param
     * @return
     */
    public static String formatData(Object param){
        String json = JackSonUtil.entityToJson(param);
        Map<String, Object> map = JackSonUtil.jsonToMap(json);
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<xml>");
        for (String key : map.keySet()) {
            stringBuffer.append("<" + key + ">" + map.get(key) + "</" + key + ">");
        }
        stringBuffer.append("</xml>");
        return stringBuffer.toString();
    }


    /**
     * 元转换成分
     * @param amount 金额
     * @return
     */
    public static String getMoneyY2F(String amount) {

        if(StringUtils.isEmpty(amount)){
            return "0";
        }

        /**
         * 金额转化为分为单位
         * 处理包含, ￥ 或者$的金额
         */
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if(index == -1){
            amLong = Long.valueOf(currency+"00");
        }else if(length - index >= 3){
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
        }else if(length - index == 2){
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
        }else{
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
        }
        return amLong.toString();
    }

    /**
     * 分转换成元
     * @param amount
     * @return
     */
    public static String getMoneyF2Y(String amount) {

        if(StringUtils.isEmpty(amount)){
            return "0";
        }
        /**
         * 金额转化为分为单位
         * 处理包含, ￥ 或者$的金额
         */
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 解析xml得到 prepay_id 预支付id
     * @param result
     * @return
     */
    public static Object getTradeOrderId(String result){

        Map<String, String> resultMap = new HashMap<>();
        Map<String, String> map = getNotifyData(result);
        if (map == null || map.size() <= 0) {
            return null;
        }
        /**
         * 返回码
         */
        String return_code = map.get("return_code");
        /**
         * 返回信息
         */
        String result_code = map.get("result_code");

        resultMap.put("return_code", return_code);
        resultMap.put("result_code", result_code);

        /**
         * return_code 和result_code 都为SUCCESS 的时候返回 预支付id
         */
        if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
            /**
             * 预支付id
             */
            resultMap.put("prepay_id", map.get("prepay_id"));
        }else if (return_code.equals("FAIL")) {
            resultMap.put("return_msg", map.get("return_msg"));
        }else if (result_code.equals("FAIL")){
            resultMap.put("err_code", map.get("err_code"));
        }
        String json = JackSonUtil.mapToJson(map);
        return JackSonUtil.jsonToEntity(json, TradeOrderResultData.class);
    }


    /**
     * 解析 回调时的xml装进map 返回
     * @param result
     * @return
     */
    public static Map<String, String> getNotifyData(String result) {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = new ByteArrayInputStream(result.getBytes());
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换失败，请检查参数是否正确");
        }
        /**
         * 得到xml根元素
         */
        Element root = document.getRootElement();
        /**
         * 遍历  得到根元素的所有子节点
         */
        @SuppressWarnings("unchecked")
        List<Element> list =root.elements();
        for(Element element:list){
            /**
             * 装进map
             */
            map.put(element.getName(), element.getText());
        }
        return map;
    }

    /**
     * 再次签名 数据处理
     * @param param 请求数据
     * @param key 密钥
     * @return
     */
    public static String signAgainFormatData(Object param, String key){
        String json = JackSonUtil.entityToJson(param);
        Map<String, Object> map = JackSonUtil.jsonToMap(json);
        if (map == null || map.size() <= 0) {
            return null;
        }
        json = MD5Util.md5(createLinkString(map)
                .replace("packages", "package") + "&key=" + key);
        return "paySign=" + json;
    }

    /**
     * （查询交易订单）数据处理
     * @param param
     * @return
     */
    public static String checkTradeOrderParamFormatData(Object param){
        String json = JackSonUtil.entityToJson(param);
        Map<String, Object> map = JackSonUtil.jsonToMap(json);
        if (map == null || map.size() <= 0) {
            return null;
        }
        return  "<xml>" +
                "<appid>" + map.get("appid") + "</appid>" +
                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
                "<transaction_id>" + map.get("transaction_id") + "</transaction_id>" +
                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
                "<sign>" + map.get("sign") + "</sign></xml>";
    }
}
