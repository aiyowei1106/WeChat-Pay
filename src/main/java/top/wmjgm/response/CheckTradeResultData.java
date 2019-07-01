package top.wmjgm.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     发起微信支付订单查询返回参数
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-07-01 14:29
 **/
@Data
@Component
@EqualsAndHashCode(callSuper = false)
public class CheckTradeResultData {

    /**
     * 返回状态码
     */
    private String return_code;

    /**
     * 返回信息
     */
    private String return_msg;

    /**
     * 以下字段在return_code为SUCCESS的时候有返回
     */

    /**
     * 小程序ID
     */
    private String appid;

    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;

    /**
     * 业务结果
     */
    private String result_code;

    /**
     * 错误代码
     */
    private String err_code;

    /**
     * 错误代码描述
     */
    private String err_code_des;

    /**
     * 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回
     */

    /**
     * 设备号
     */
    private String device_info;

    /**
     * 用户标识
     */
    private String openid;

    /**
     * 是否关注公众账号
     */
    private String is_subscribe;

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 交易状态
     */
    private String trade_state;

    /**
     * 付款银行
     */
    private String bank_type;

    /**
     * 标价金额
     */
    private String total_fee;

    /**
     * 应结订单金额
     */
    private String settlement_total_fee;

    /**
     * 标价币种
     */
    private String fee_type;

    /**
     * 现金支付金额
     */
    private String cash_fee;

    /**
     * 现金支付币种
     */
    private String cash_fee_type;

    /**
     * 代金券金额
     */
    private String coupon_fee;

    /**
     * 代金券使用数量
     */
    private int coupon_count;

    /**
     * 代金券类型
     */
    private String coupon_type_$n;

    /**
     * 代金券ID
     */
    private String coupon_id_$n;

    /**
     * 单个代金券支付金额
     */
    private String coupon_fee_$n;

    /**
     * 微信支付订单号
     */
    private String transaction_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 支付完成时间
     */
    private String time_end;

    /**
     * 交易状态描述
     */
    private String trade_state_desc;

}
