package top.wmjgm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * <p>
 *     MD5加密工具
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 01:51
 **/
@Slf4j
public class MD5Util {

    /**
     * MD5加密
     * @param signTemp
     * @return
     */
    public static String md5(String signTemp) {
        String encodeStr= DigestUtils.md5DigestAsHex(signTemp.getBytes());
        return encodeStr.toUpperCase();
    }

    /**
     * MD5验证方法
     * @param signTemp 明文
     * @param md5 密文
     * @return
     */
    public static boolean verifyMD5(String signTemp, String md5) {
        /**
         * 根据传入的密钥进行验证
         */
        String md5Text = md5(signTemp);
        if(md5Text.equalsIgnoreCase(md5))
        {
            log.debug("MD5验证通过");
            return true;
        }

        return false;
    }
}
