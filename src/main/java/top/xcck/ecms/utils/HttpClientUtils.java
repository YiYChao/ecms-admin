package top.xcck.ecms.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Description:HttpClient的工具类
 * @author: YiYChao
 * @Date: 2020/2/8 15:25
 * @Version: V1.0
 */
@Component
public class HttpClientUtils {
    private PoolingHttpClientConnectionManager manager;

    public HttpClientUtils() {
        // 初始化连接对象
        manager = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        manager.setMaxTotal(100);
        // 设置每台主机的最大连接数
        manager.setDefaultMaxPerRoute(10);
    }

    /**
     * @Description: 设置Get请求的相关信息
     * @Param: null
     * @return: void
     * @Author：YiYChao
     * @date: 2020/2/8 17:19
     */
    private RequestConfig getConnfig() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000)       // 设置连接超时时间
                .setConnectionRequestTimeout(2000)      // 设置请求超时时间
                .setSocketTimeout(3000)             // 设置资源传输超时时间
                .build();
        return config;
    }

    /**
     * @Description: 抓取传入的URL地址对应网络资源
     * @Param: String url 网络地址
     * @return: String 字符串形式的网络资源
     * @Author：YiYChao
     * @date: 2020/2/8 17:10
     */
    public String doGetHtml(String url) {
        // 1、获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).build();
        // 2、创建Get请求
        HttpGet get = new HttpGet(url);
        get.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36");
        get.setConfig(getConnfig());        // 配置请求信息
        // 3、发起请求，获取响应数据
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(get);
            // 请求成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 4、解析响应数据
                HttpEntity responseEntity = httpResponse.getEntity();
                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse == null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 抓取失败，则返回空串
        return "";
    }

    /**
     * @Description: 下载网络图片
     * @Param: 网络图片的地址
     * @return: 保存后图片的名称
     * @Author：YiYChao
     * @date: 2020/2/8 17:20
     */
    public String doGetImage(String url, String path, String picName) {
        // 1、获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).build();
        // 2、创建Get请求
        HttpGet get = new HttpGet(url);
        get.setConfig(getConnfig());        // 配置请求信息
        // 3、发起请求，获取响应数据
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(get);
            // 请求成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 4、解析响应数据
                HttpEntity responseEntity = httpResponse.getEntity();
                if (responseEntity != null) {
                    String extName = url.substring(url.lastIndexOf('.'));   // 获取图片的后缀名,带参数的
                    // 后缀名中包含有图片大小的补充参数
                    if (extName.indexOf('_') > 0){
                        extName = extName.substring(0, extName.indexOf('_'));       // 去除参数
                    }
                    // 未指定新的图片名称
                    if (picName == null || "".equals(picName)){
                        picName = UUID.randomUUID().toString() + extName;    // 新的文件的名称
                    }else {     // 指定新的图片名称
                        picName += extName;
                    }
                    // 去除保存路径中的非法字符
                    path = path.replaceAll("\\*", "").replaceAll("/", "").replaceAll("\\?", "").replaceAll("\\.", "");
                    // 判断文件夹是否存在
                    File tmp = new File(path);
                    if (!tmp.exists()) {
                        tmp.mkdirs();       // 创建文件夹
                    }
                    // 获取文件输出流
                    OutputStream outputStream = new FileOutputStream(new File(path + picName));
                    responseEntity.writeTo(outputStream);   // 保存图片
                    outputStream.close();   // 关闭输出流
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse == null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 下载失败，则返回空串
        return "";
    }
}
