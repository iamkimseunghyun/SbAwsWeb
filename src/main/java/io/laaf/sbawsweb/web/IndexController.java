package io.laaf.sbawsweb.web;

import io.laaf.sbawsweb.config.auth.LoginUser;
import io.laaf.sbawsweb.config.auth.dto.SessionUser;
import io.laaf.sbawsweb.web.dto.PostsResponseDto;
import io.laaf.sbawsweb.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            log.info("로그인 유저 = {}", user.getName());
            // "username"으로 키 값 설정하면 디폴트 환경변수와 충돌나서 작동 안함
            model.addAttribute("user", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
