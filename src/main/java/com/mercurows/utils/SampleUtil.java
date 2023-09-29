package com.mercurows.utils;
// This file is auto-generated, don't edit it. Thanks.

import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.*;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;

public class SampleUtil {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        // java.util.List<String> args = java.util.Arrays.asList(args_);
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.dysmsapi20170525.Client client = SampleUtil.createClient("LTAI5tQxQGe2uQbvBLcHWLwA", "Kyi2JwCsLGXxz88Iak57w2Rjk02rDI");
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                // .setPhoneNumbers("15323629383")
                // .setSignName("Mercurows")
                // .setTemplateCode("SMS_286180820")
                // .setTemplateParam("{\"code\":\"" + "12345" + "\"}");
                .setSignName("Mercurows")
                .setTemplateCode("SMS_463225619")
                .setPhoneNumbers("15323626350")
                .setTemplateParam("{\"code\":\"1234\"}");

                // .setTemplateCode("您正在申请手机注册，验证码为：${code}，5分钟内有效!")
                // .setTemplateParam("1234");
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendBatchSmsResponse = client.sendSmsWithOptions(sendSmsRequest,
                    new com.aliyun.teautil.models.RuntimeOptions());
            System.out.println(new Gson().toJson(sendBatchSmsResponse.getBody()));
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}

