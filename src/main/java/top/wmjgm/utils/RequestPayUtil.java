package top.wmjgm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p>
 *     接口请求工具
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 02:22
 **/
@Slf4j
public class RequestPayUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 发送http请求
     * @param url 请求url
     * @param data 请求数据
     * @return
     */
    public static String sendAppPayRequest(String url, String data) {

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, data, String.class);
        return responseEntity.getBody();

    }

    /**
     * 支付成功回调
     * @param request
     * @param response
     * @throws Exception
     */
    public void callbackProgramNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        /**
         * stringBuilder：微信返回的xml
         */
        String notityXml = stringBuilder.toString();
        String resultXml = "";
        log.info("接收到的报文：" + notityXml);
        Map<String, String> map = WeChatPayUtil.getNotifyData(notityXml);
        String returnCode = map.get("return_code");
        String outTradeNo = map.get("out_trade_no") + "";
        String totalFee = map.get("total_fee") + "";
        String attach = map.get("attach") + "";
        if ("SUCCESS".equals(returnCode)) {
            /**
             * 验证签名是否正确
             */
            if (returnCode.equals("SUCCESS")) {
                /**
                 * 此处添加自己的业务逻辑代码start
                 * 更新数据库
                 */

            }
        } else {
            resultXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        resultXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>"
                + "</xml> ";
        log.info("微信支付回调数据结束");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resultXml.getBytes());
        out.flush();
        out.close();
    }
}
