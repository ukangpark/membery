package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("map")
public class MapController {
	
	@Autowired
	public MapService mapService;
	
	@GetMapping("mapMain")
	public void mapMain(ModelMap model) {
		String apiKey = mapService.getKakao_javaScript_key();
		model.addAttribute("apiKey", apiKey);
	}
	
	// 찜 목록
	@GetMapping("mapMypage")
	public void likePlace(Model model, Authentication authentication,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		String apiKey = mapService.getKakao_javaScript_key();
		model.addAttribute("apiKey", apiKey);
		String userId = authentication.getName();
		Map<String, Object> result = mapService.likePlace(userId);
		model.addAllAttributes(result);
		
	}
	
	@PostMapping("addPlace")
	@ResponseBody
	public ResponseEntity<String> bookAccept(@RequestBody Place place, Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.ok()
					.body("로그인 후 이용하실 수 있습니다.");
		}else {
			String userId = authentication.getName();
			String address = place.getAddress();
			//테이블에 해당 찜 가게가 있는지 조회 service
			int check = mapService.selectMapList(userId,address);
			if(check != 0) {
				return ResponseEntity.ok()
						.body("이미 찜한 곳 입니다.");
			} else {
				place.setMemberId(userId);
				mapService.insertMapList(place);
				return ResponseEntity.ok()
						.body("찜 완료!"); 
			}
			
		}
	}
	
	
}
