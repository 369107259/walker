package alone.walker.spring.controller;


import alone.walker.spring.annotation.EnjoyAutowired;
import alone.walker.spring.annotation.EnjoyController;
import alone.walker.spring.annotation.EnjoyRequestMapping;
import alone.walker.spring.service.IAnnotationService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:33
 */
@EnjoyController
@EnjoyRequestMapping("/annotation")
public class AnnotationController {
    @EnjoyAutowired
    private IAnnotationService annotationService;

    @EnjoyRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response, @RequestParam("name")String name, @RequestParam("age")String age){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String query = annotationService.query(name, age);
        if (writer != null) {
            writer.write(query);
        }
    }
}
