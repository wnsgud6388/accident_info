package com.projectITS.accident_info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectITS.accident_info.dto.Accident_infoDTO;
import com.projectITS.accident_info.dto.PageRequestDTO;
import com.projectITS.accident_info.service.Accident_infoService;
import com.projectITS.accident_info.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import com.projectITS.accident_info.entity.Member;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Controller
@RequestMapping("/accident_info")
@Slf4j

public class HomeController {

    @Autowired
    private Accident_infoService accident_infoService;

    @Autowired
    private MemberService memberService;

    
    // -------------------------------- 홈화면 지도 마커 표출 -----------------------
    
    /**
    * Simply selects the home view to render by returning its name.
    */
   @GetMapping("/")
   public String home(HttpSession ses, HttpServletResponse response){
    if(ses.getAttribute("loginMember") == null){
        return "accident_info/loginForm";
    } else{
        return "accident_info/home";
    }   

   }
   
   @GetMapping("/dataWorks_hjh")
   public String showDataWorksHjh() {

       return "accident_info/dataWorks_hjh";
   }

   @GetMapping("/dataWorks_uyj")
   public String showDataWorksUyj() {

       return "accident_info/dataWorks_uyj";
   }

   @GetMapping("/dataWorks_lar")
   public String showDataWorksLar() {

       return "accident_info/dataWorks_lar";
   }



   @GetMapping("/loginForm")
    public String getMethodName() {
        return "accident_info/loginForm";
    }

   
     @PostMapping("/login")
	public String login(@RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd, HttpSession session, Model model) {
		log.info(userId + ": " + userPwd);
        String result = "";

		// 로그인 시키는 메서드
		Member loginMember = memberService.loginMember(userId, userPwd);

			if (loginMember != null) {
				// 로그인 한 유저 세션에 저장하기
				session.setAttribute("loginMember", loginMember);
				model.addAttribute("status", "loginSuccess");
                result = "/accident_info/home";
			} else { // 로그인 실패시
				log.info("로그인 실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				model.addAttribute("status", "loginFail");
                result = "/accident_info/loginForm";
			}
            return result;
	}

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
       
        session.removeAttribute("loginMember");
        return "redirect:/accident_info/loginForm";
    }


    @GetMapping("/map")
   
   public String map(Model model, HttpSession ses, HttpServletResponse response) {

    if(ses.getAttribute("loginMember") == null){
        return "accident_info/loginForm";
    } else{
        try{
            model.addAttribute("acc", buildURL("acc"));
            model.addAttribute("cor", buildURL("cor"));
            model.addAttribute("etc", buildURL("etc"));
        } catch(Exception e){
            e.printStackTrace();
        }
   
      return "accident_info/map";
    }   
        
    }

    public String buildURL(String eventType) throws Exception {

        StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/eventInfo"); /*URL*/
      
      BufferedReader rd;
      
        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("424d0c6203fb4d9c946dca3f7c3145d8", "UTF-8")); /*공개키*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8")); /*도로유형*/
        urlBuilder.append("&" + URLEncoder.encode("eventType","UTF-8") + "=" + URLEncoder.encode(eventType, "UTF-8")); /*이벤트유형*/
//        urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode("126.800000", "UTF-8")); /*최소경도영역*/
//        urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode("127.890000", "UTF-8")); /*최대경도영역*/
//        urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode("34.900000", "UTF-8")); /*최소위도영역*/
//        urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode("35.100000", "UTF-8")); /*최대위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*출력타입*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
        System.out.println("Response code: " + conn.getResponseCode());
        
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
         rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
         sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        String act = sb.toString();
        
        return act;
    }

    
    


    // -------------------------------- 게시판 작업 --------------------------------
    @GetMapping("/otherWeb")
    public String showOterWeb() {
       

        return "accident_info/otherWeb";
    }
    
    
    @GetMapping("/list")
    public String listAll(PageRequestDTO pageRequestDTO, Model model, HttpSession ses, HttpServletResponse response) {
        
        if(ses.getAttribute("loginMember") == null){
            return "accident_info/loginForm";
        } else{

            
        log.info("List Memos 컨트롤러!");
        // return "/guestbook/list"; 와 같다
        log.info(pageRequestDTO.toString());

        // if(pageRequestDTO.getSearchType().equals("-1")){
        //     pageRequestDTO.setSearchType(null);
        // }

        model.addAttribute("result",accident_infoService.getList(pageRequestDTO));
        model.addAttribute("pageRequestDTO", pageRequestDTO);

        System.out.println(accident_infoService.getList(pageRequestDTO).isNext());
            return "accident_info/list";
        }   
        
        
    }

    @GetMapping("/showSaveForm")
    public String showSaveForm(HttpSession ses) {
        if(ses.getAttribute("loginMember") == null){
            return "accident_info/loginForm";
        } else{
            return "accident_info/showSaveForm";
        }
    }

    @PostMapping("/save")
    public String saveGuestBook(Accident_infoDTO accident_infoDTO, RedirectAttributes redirectAttributes, HttpSession ses, HttpServletResponse response) {
        
            log.info("save : " + accident_infoDTO.toString());

            Long savedGno = accident_infoService.register(accident_infoDTO);

            if(savedGno != null) {
                redirectAttributes.addFlashAttribute("status", "success");
            } else { 
                redirectAttributes.addFlashAttribute("status", "fail");
            }
        
        return "redirect:/accident_info/list";

    }
    
    @GetMapping("/read")
    public String guestBookDetailPage(@RequestParam("gno") Long gno, Model model, HttpSession ses, HttpServletResponse response) {    
       
        if(ses.getAttribute("loginMember") == null){
            return "accident_info/loginForm";
        } else{
            model.addAttribute("result",accident_infoService.read(gno));
            return "accident_info/read";
        }

        
    }


}

