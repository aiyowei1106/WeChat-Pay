package top.wmjgm.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     发起微信支付订单查询请求参数
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-07-01 13:23
 **/
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CheckTradeOrderParam {

    /**
     * 小程序ID
     */
    private String appid;

    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 微信订单号
     * 微信订单号和商户订单号 二选一
     */
    private String transaction_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

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
     */
    private String sign_type;

}
