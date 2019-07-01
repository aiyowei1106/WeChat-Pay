package top.wmjgm.constants;

/**
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 16:20
 **/
public class Constants {

    /**
     * 小程序ID
     */
    public final static String APP_ID = "wx8397f8696bXXXXXX";

    /**
     * 商户号
     */
    public final static String MCH_ID = "XXXXXX";

    /**
     * 回调url
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    public final static String NOTIFY_URL = "http://a31ef7db.ngrok.io/WeChatPay/WeChatPayNotify";

    /**
     * 交易类型 小程序：JSAPI
     */
    public final static String TRADE_TYPE = "JSAPI";

    /**
     * 商户密钥
     */
    public final static String PARTNER_KEY = "8A627A4578ACE384017CXXXXXX";

    /**
     * 签名方式
     */
    public final static String SIGN_TYPE = "MD5";

    /**
     * 统一下单URL
     */
    public final static String TRADE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单URL
     */
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
}
