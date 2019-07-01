package top.wmjgm.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     统一下单返回参数
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-29 23:07
 **/
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeOrderResultData {

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
     * 设备号
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
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 预支付订单ID
     */
    private String prepay_id;

    /**
     * 二维码链接
     */
    private String code_url;
}
