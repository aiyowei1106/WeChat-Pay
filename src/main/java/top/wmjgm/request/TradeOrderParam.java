package top.wmjgm.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     统一下单请求参数
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-29 23:03
 **/
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeOrderParam {

    /**
     * 小程序id
     */
    private String appid;

    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 设备号
     * 非必填
     */
    private String device_info;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     * 非必填
     */
    private String sign_type;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     * 非必填
     */
    private String detail;

    /**
     * 附加数据
     * 非必填
     */
    private String attach;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 标价币种
     * 非必填
     */
    private String fee_type;

    /**
     * 标价金额
     */
    private String total_fee;

    /**
     * 终端IP
     */
    private String spbill_create_ip;

    /**
     * 交易起始时间
     * 非必填
     */
    private String time_start;

    /**
     * 交易结束时间
     * 非必填
     */
    private String time_expire;

    /**
     * 订单优惠标记
     * 非必填
     */
    private String goods_tag;

    /**
     * 通知地址
     */
    private String notify_url;

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 商品ID
     * 非必填
     */
    private String product_id;

    /**
     * 指定支付方式
     * 非必填
     */
    private String limit_pay;

    /**
     * 用户标识
     * 非必填
     */
    private String openid;

    /**
     * 电子发票入口开放标识
     * 非必填
     */
    private String receipt;

    /**
     * 场景信息
     *  非必填
     */
    private String scene_info;
}
