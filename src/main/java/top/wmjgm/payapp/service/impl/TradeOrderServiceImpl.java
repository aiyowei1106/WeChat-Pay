package top.wmjgm.payapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.wmjgm.constants.Constants;
import top.wmjgm.request.CheckTradeOrderParam;
import top.wmjgm.response.CheckTradeResultData;
import top.wmjgm.response.SignAgainResultData;
import top.wmjgm.request.TradeOrderParam;
import top.wmjgm.payapp.service.TradeOrderService;
import top.wmjgm.utils.JackSonUtil;
import top.wmjgm.utils.RequestPayUtil;
import top.wmjgm.utils.WeChatPayUtil;

import java.util.Map;

/**
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 15:43
 **/
@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Autowired
    private SignAgainResultData signAgainResultData;
    @Autowired
    private CheckTradeOrderParam checkTradeOrderParam;

    /**
     * 发起请求 创建预支付订单
     * @param tradeOrderParam 预支付订单参数
     * @return
     */
    @Override
    public Object createTradeOrder(TradeOrderParam tradeOrderParam) {

        /**
         * 调用支付统一下单
         */
        String timeStart = WeChatPayUtil.getTimeStart();
        tradeOrderParam.setAppid(Constants.APP_ID)
                .setMch_id(Constants.MCH_ID)
                .setNonce_str(WeChatPayUtil.getRandomKey())
                .setOut_trade_no(WeChatPayUtil.getOrderNumber())
                .setTime_start(timeStart)
                .setTime_expire(WeChatPayUtil.getTimeExpire(timeStart, 1))
                .setNotify_url(Constants.NOTIFY_URL)
                .setTrade_type(Constants.TRADE_TYPE)
                .setTotal_fee(WeChatPayUtil.getMoneyY2F(tradeOrderParam.getTotal_fee()))
                .setSign(WeChatPayUtil.getSign(tradeOrderParam));
        String result = RequestPayUtil.sendAppPayRequest(Constants.TRADE_ORDER_URL, WeChatPayUtil.formatData(tradeOrderParam));

        /**
         * 返回预付单信息
         */
        Map<String, String> resultData = WeChatPayUtil.getNotifyData(result);
        String prepay_id = resultData.get("prepay_id");
        if (StringUtils.isEmpty(prepay_id)) {
            return WeChatPayUtil.getTradeOrderId(result);
        }


        /**
         * 再次签名
         */
        signAgainResultData.setAppId(Constants.APP_ID)
                .setTimeStamp(WeChatPayUtil.getTimeStamp())
                .setNonceStr(WeChatPayUtil.getRandomKey())
                .setPackages("prepay_id=" + prepay_id)
                .setSignType(Constants.SIGN_TYPE)
                .setSign(WeChatPayUtil.getSign(signAgainResultData));
        return signAgainResultData;

    }

    /**
     * 查询订单
     * @param orderID 订单编号（商户系统内部订单号）
     * @return
     */
    @Override
    public Object checkTradeOrder(String orderID) {

        checkTradeOrderParam.setAppid(Constants.APP_ID)
                .setMch_id(Constants.MCH_ID)
                .setNonce_str(WeChatPayUtil.getRandomKey())
                .setSign_type(Constants.SIGN_TYPE)
                .setOut_trade_no(orderID)
                .setSign(WeChatPayUtil.getSign(checkTradeOrderParam));
        String result = RequestPayUtil.sendAppPayRequest(Constants.CHECK_ORDER_URL, WeChatPayUtil.formatData(checkTradeOrderParam));
        Map<String, String> data = WeChatPayUtil.getNotifyData(result);
        CheckTradeResultData checkTradeResultData = (CheckTradeResultData) JackSonUtil.jsonToEntity(JackSonUtil.mapToJson(data), CheckTradeResultData.class);

        /**
         * 返回金额处理
         */
        checkTradeResultData.setTotal_fee(WeChatPayUtil.getMoneyF2Y(checkTradeResultData.getTotal_fee()));
        checkTradeResultData.setSettlement_total_fee(WeChatPayUtil.getMoneyF2Y(checkTradeResultData.getSettlement_total_fee()));
        checkTradeResultData.setCash_fee(WeChatPayUtil.getMoneyF2Y(checkTradeResultData.getCash_fee()));
        checkTradeResultData.setCoupon_fee(WeChatPayUtil.getMoneyF2Y(checkTradeResultData.getCoupon_fee()));
        checkTradeResultData.setCoupon_fee_$n(WeChatPayUtil.getMoneyF2Y(checkTradeResultData.getCoupon_fee_$n()));

        return checkTradeResultData;

    }
}
