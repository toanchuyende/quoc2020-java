package vn.agileviet.quoc2020.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	@RequestMapping("/chat")
	public String index() {
		return "chat";
	}
}
