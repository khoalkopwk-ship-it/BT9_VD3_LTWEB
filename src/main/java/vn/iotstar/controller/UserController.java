package vn.iotstar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        model.addAttribute(
                "users",
                userService.findAll(keyword, page, size)
        );

        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);

        return "users/list";
    }

    @GetMapping("/create")
    public String create(Model model) {

        UserDTO dto = new UserDTO();

        dto.setEnabled(true);
        dto.setRoleName("ROLE_USER");

        model.addAttribute("userDTO", dto);
        model.addAttribute("mode", "create");

        return "users/form";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute UserDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirect
    ) {

        if (result.hasErrors()) {
            model.addAttribute("mode", "create");
            return "users/form";
        }

        try {

            userService.create(dto);

            redirect.addFlashAttribute(
                    "success",
                    "Tạo user thành công. Mật khẩu mặc định: 123456"
            );

            return "redirect:/users";

        } catch (IllegalArgumentException e) {

            result.reject(
                    "user.error",
                    e.getMessage()
            );

            model.addAttribute("mode", "create");

            return "users/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "userDTO",
                userService.findById(id)
        );

        model.addAttribute(
                "mode",
                "edit"
        );

        return "users/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute UserDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirect
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "mode",
                    "edit"
            );

            return "users/form";
        }

        try {

            userService.update(
                    id,
                    dto
            );

            redirect.addFlashAttribute(
                    "success",
                    "Cập nhật user thành công."
            );

            return "redirect:/users";

        } catch (IllegalArgumentException e) {

            result.reject(
                    "user.error",
                    e.getMessage()
            );

            model.addAttribute(
                    "mode",
                    "edit"
            );

            return "users/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirect
    ) {

        userService.delete(id);

        redirect.addFlashAttribute(
                "success",
                "Xóa user thành công."
        );

        return "redirect:/users";
    }
}