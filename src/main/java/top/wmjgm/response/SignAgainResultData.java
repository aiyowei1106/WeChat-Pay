package top.wmjgm.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * <p>
 *      再次签名参数
 * </p>
 *
 * @author: Hanne hll941106@163.com
 * @date: 2019-07-01 00:20
 **/
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SignAgainResultData {

    /**
     * 小程序ID
     */
    private String appId;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机串
     */
    private String nonceStr;

    /**
     * 数据包
     */
    private String packages;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 签名
     */
    private String sign;
}
