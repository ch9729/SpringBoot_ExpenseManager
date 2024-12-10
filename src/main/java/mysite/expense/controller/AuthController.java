package mysite.expense.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mysite.expense.dto.UserDTO;
import mysite.expense.entity.User;
import mysite.expense.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService uService;

    // 로그인 페이지 보여주기
    @GetMapping({"/login","/" })
    public String showLoginPage() {

        return "login";
    }

    // 회원가입
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO user,
                           BindingResult bindingResult,
                           Model model) {
        System.out.println(user);
        if(bindingResult.hasErrors()) {
            return "register";
        }
        //DB에 저장
        uService.save(user);
        model.addAttribute("successMsg", true);
        return "login";
    }


}
