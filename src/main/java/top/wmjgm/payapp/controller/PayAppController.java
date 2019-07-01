package top.wmjgm.payapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wmjgm.payapp.service.TradeOrderService;
import top.wmjgm.request.TradeOrderParam;

/**
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 16:09
 **/
@Controller
@RequestMapping("/wechat-pay")
public class PayAppController {

    @Autowired
    private TradeOrderService tradeOrderService;

    /**
     * 发起请求 创建预支付订单
     * @param tradeOrderParam 预支付订单参数
     * @return
     */
    @ResponseBody
    @PostMapping("/create-trade-order")
    public Object createTradeOrder(TradeOrderParam tradeOrderParam){

        Object obj = tradeOrderService
                .createTradeOrder(tradeOrderParam);
        if (obj == null){
            return "请求失败，请稍后重试";
        }
        return obj;

    }

    /**
     * 查询订单
     * @param orderID 订单编号（商户系统内部订单号）
     * @return
     */
    @ResponseBody
    @PostMapping("/check-trade-order")
    public Object checkTradeOrder(String orderID){

        Object obj = tradeOrderService.checkTradeOrder(orderID);
        if (obj == null){
            return "请求失败，请稍后重试";
        }
        return obj;

    }
}
