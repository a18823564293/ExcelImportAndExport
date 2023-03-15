package cn.yiynx.example.util;

import lombok.experimental.UtilityClass;
import lombok.var;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class HttpResponseUtil {

    public static OutputStream excelOutput(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String name = URLEncoder.encode(fileName , "UTF-8").replaceAll("\\+", "%20");
        String s = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + name + "_" + s + ".xlsx");
        return response.getOutputStream();
    }


}
