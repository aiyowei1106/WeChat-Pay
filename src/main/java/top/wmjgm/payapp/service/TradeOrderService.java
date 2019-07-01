package top.wmjgm.payapp.service;

import top.wmjgm.request.TradeOrderParam;

/**
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 15:45
 **/
public interface TradeOrderService {

    /**
     * 发起请求 创建预支付订单
     * @param tradeOrderParam 预支付订单参数
     * @return
     */
    Object createTradeOrder(TradeOrderParam tradeOrderParam);

    /**
     * 查询订单
     * @param orderID 订单编号（商户系统内部订单号）
     * @return
     */
    Object checkTradeOrder(String orderID);
}
