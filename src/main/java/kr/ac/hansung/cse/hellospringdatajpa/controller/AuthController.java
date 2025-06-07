package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        model.addAttribute("success", "회원가입이 완료되었습니다. 로그인 해주세요.");
        return "login";
    }

    // @GetMapping("/login")
    // public String loginPage(@RequestParam(value = "error", required = false) String error,
    //                         @RequestParam(value = "logout", required = false) String logout,
    //                         Model model) {
    //     if (error != null) model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
    //     if (logout != null) model.addAttribute("message", "로그아웃 되었습니다.");
    //     return "login";
    // }
}