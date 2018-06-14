package com.mileworks.jetonline.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Pako_GzipUtils {

    /**
     * @param str：正常的字符串
     * @return 压缩字符串 类型为：  ³)°K,NIc i£_`Çe#  c¦%ÂXHòjyIÅÖ`
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }


    /**
     * @param str：类型为： ³)°K,NIc i£_`Çe#  c¦%ÂXHòjyIÅÖ`
     * @return 解压字符串  生成正常字符串。
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }

    /**
     * @param jsUriStr :字符串类型为：%1F%C2%8B%08%00%00%00%00%00%00%03%C2%B3)%C2%B0K%2CNI%03c%20i%C2%A3_%60%C3%87e%03%11%23%C2%82%0Dc%C2%A6%25%C3%82XH%C3%B2jyI%C3%85%05%C3%96%60%1E%00%17%C2%8E%3Dvf%00%00%00
     * @return 生成正常字符串
     * @throws IOException
     */
    public static String unCompressURI(String jsUriStr) throws IOException {
        String decodeJSUri = URLDecoder.decode(jsUriStr, "UTF-8");
        String gzipCompress = uncompress(decodeJSUri);
        return gzipCompress;
    }

    /**
     * @param strData :字符串类型为： 正常字符串
     * @return 生成字符串类型为：%1F%C2%8B%08%00%00%00%00%00%00%03%C2%B3)%C2%B0K%2CNI%03c%20i%C2%A3_%60%C3%87e%03%11%23%C2%82%0Dc%C2%A6%25%C3%82XH%C3%B2jyI%C3%85%05%C3%96%60%1E%00%17%C2%8E%3Dvf%00%00%00
     * @throws IOException
     */
    public static String compress2URI(String strData) throws IOException {
        String encodeGzip = compress(strData);
        String jsUriStr = URLEncoder.encode(encodeGzip, "UTF-8");
        return jsUriStr;
    }

    public static void main(String[] args) throws Exception {

        String raw = "{\"menu\": {\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "  }\n" +
                "}}";

        String zipRaw = "%1F%C2%8B%08%00%00%00%00%00%00%00u%C2%8F%3Do%C2%830%10%C2%86%C3%A7%C3%B0%2BN%C2%9E%C3%93JYR%C2%92%C2%8D%C2%8A%0AE%22%C3%AA%40%C2%87JU%C2%87%0B%5C%29%C2%8A%3F%C2%90m%C2%AA%C2%A6%11%C3%BF%C2%BD%C3%86%C3%90H%0E%29%C2%8B%C3%B1%C3%9D%C3%BB%C3%B1%C3%B8%1C%C2%81%C3%BBX%C3%8D%C2%951%C2%A8Ol%0Bg%3F%C3%B1S%C3%9BXNn%C3%84%C3%A8%1BE%C3%8B%09.%C2%B2e%C2%B4X%C2%B0l%C2%B8%C2%A5%C3%8DW%60%0A%C2%8D%C2%85WN%C3%92%C2%BC1v%C2%A6%C3%B5z%C2%BF%7E%C2%92%C3%B6%0A+%C3%90%C3%ACR%1F%C2%98%C3%AD%C3%B31%C3%93%C2%A5%16J%C3%9B%C3%84%C3%8C%C3%86%3E%C3%AD%C2%85%C2%B4%C3%B0%1B%C2%8B%C2%B2B%5DAF%C2%924%C3%B2%C3%A6%C2%87*%C3%98%C2%A3%3Ev-%C3%A4%28%C3%AB%0Ek%C2%BA8%C2%93R%2By%12%C2%B3%C3%84%C3%A4p%C3%904%C2%BC%C2%93%C3%AD%C2%8Ag%C2%88%C3%A3%C2%87%C3%8Dv%C2%B5%C2%89%C3%97acJ%1F%C3%BF%C3%92%C3%BB%17%C2%B4%C2%A8q%C3%88H%40%C2%90%C3%85%3B1B%C3%B0%09b%09%C2%9DqhVA%C2%A9%09-%C3%81%C3%95%C3%9E%C2%80%C3%A9%C3%8AO%40%03%C2%A9*%1F%C2%95%3A%C3%9E%C3%BF%C3%95O%C3%BD%05Q%C3%82%C2%8Dr%0Do%C3%8C%C3%83%03%7Bu%C3%87%C3%BBM%C2%A0%3E%40w%C3%96%01lld3C%1F%C3%9D%C2%BE%C2%8D%7F%7D%C3%94%C3%BF%02%16%C2%AD%14%3BF%02%00%00";
        System.out.println("原来字段长度：" + raw.getBytes().length);
        System.out.println("现在未encode字段长度：" + compress(raw).getBytes().length);
        System.out.println("现在encode字段长度：" + zipRaw.getBytes().length);

        System.out.println("原来压缩" + compress(raw));
        System.out.println("原来解压" + uncompress(compress(raw)));
        System.out.println("原来压缩encode" + compress2URI(raw));
        System.out.println("原来解压decode" + unCompressURI(zipRaw));

    }


}
