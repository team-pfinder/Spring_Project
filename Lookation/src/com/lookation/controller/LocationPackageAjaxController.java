// LocationPackageAjaxController.java

package com.lookation.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.ILocationDetailDAO;

public class LocationPackageAjaxController implements Controller
{
   private ILocationDetailDAO dao;

   public void setDao(ILocationDetailDAO dao)
   {
      this.dao = dao;
   }

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      ModelAndView mav = new ModelAndView();

      // 데이터 수신(locationDetail.jsp 페이지로부터... 로그인한 memberCode 수신)
      String loc_code = request.getParameter("loc_code");

      String parseJson = "";

      ArrayList<String> packageInfo = new ArrayList<String>();

      try
      {
         packageInfo = dao.detailPackage(loc_code);
         
         String tmp = "";
         
         // AJAX용 JSON 만들기
         for (int i = 0; i < packageInfo.size(); i++)
         {
            tmp +="{\"applyDate\":"+ "\"" + packageInfo.get(i) + "\"";
            
            if(i==packageInfo.size()-1){ tmp += "}"; }else{ tmp += "},"; }
         }
         
         response.setContentType("text/html;charset=utf-8");
         PrintWriter out=response.getWriter();
         parseJson = "[" + tmp + "]";
         out.print(parseJson.toString()); 

         mav.addObject("data", parseJson);

         mav.setViewName("../WEB-INF/views/ajax/LocationDetailAjax.jsp");

      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      return mav;
   }
   
}