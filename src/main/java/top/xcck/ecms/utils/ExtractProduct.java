package top.xcck.ecms.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import top.xcck.ecms.model.pojo.TbProduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *  @Description: 提取商品信息的工具类
 *  @author YiYChao
 *  @Date: 2020/3/31 17:18
 *  @version V1.0
 */
@Component
public class ExtractProduct {

    /**
     * @Description: 从网页源码中提取商品信息
     * @param  content 商品源码信息
     * @return List<TbProduct> 返回商品列表
     * @author YiYChao
     * @date 2020/3/31 17:18
     */
    public List<TbProduct> extract(String content) {
        List<TbProduct> list = new ArrayList<>();
        Document document = Jsoup.parse("<html><head></head><body><table>" + content + "</table></body></html>");
        Elements trs = document.getElementsByTag("tr");
        for (Element tr : trs) {
            TbProduct product = new TbProduct();
            Elements tds = tr.getElementsByTag("td");
            // 获得商品图片
            String src = tds.get(1).getElementsByTag("img").attr("src");
            if (!src.contains("/mcmp/webstatic/")){
                product.setImgUrl(src);     // 设置商品图片
            }
            // 获得商品名称
            String title = tds.get(1).select("p.infoname").attr("title");
            product.setTitle(title);
            // 获得苏宁编码
            String snCode = tds.get(1).select("p.c999").last().attr("title");
            if (snCode != null && !"".equals(snCode)) {        // 抓取不到商品编码，跳过继续
                product.setCode(snCode);
            }
            // 获得类目
            String category = tds.get(2).getElementsByTag("p").attr("title");
            product.setCategory(category);
            // 获得品牌
            String brand = tds.get(3).getElementsByTag("p").attr("title");
            product.setBrand(brand);
            // 获得发布时间
            String release = tds.get(4).text();
            product.setReleased(release);
            // 获得产品状态
            String pStatus = tds.get(5).text();
            product.setPdtStatus(pStatus);
            // 获得内容状态
            String cStatus = tds.get(6).text();
            product.setCntStatus(cStatus);
            // 设置更新时间
            product.setUpdated(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
            // 将产品加入到列表
//            String parent = tr.attr("class");
//            if ("children-tr".equals(parent)){  // 字码
//                product.setParent(1L);
//            }
            list.add(product);
        }
        return list;
    }

    /**
     * @Description: 找出商品之间的父子关系
     * @param  content 商品源码内容
     * @return List<TbProduct> 返回商品列表
     * @author YiYChao
     * @date 2020/3/31 18:06
     */
    public List<TbProduct> productList(String content){
        boolean flag = true;
        int count = 0;      // 记录向前遍历的次数
        String parent = ""; // 记录父级的商品名称
        List<TbProduct> list = extract(content);
        for (int i = 0; i < list.size(); ) {
            if (list.get(i).getParent().longValue() == 1L && "".equals(parent)){     // 子码未找到父码
                i -= 1;
                count ++;
            }else {
                parent = list.get(i - count).getTitle();    // 获得商品标题
                while (count > 0){
                    list.get(i - count).setNote(parent);    // 将父级编码插入备注字段
                    count --;
                }
                parent = "";
                i ++;
            }
        }
        return list;
    }
}
