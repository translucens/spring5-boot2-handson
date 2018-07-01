package com.example.web.controller;

import com.example.persistence.entity.Customer;
import com.example.service.CustomerService;
import com.example.web.form.CustomerForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CustomerController {

    /** 必要があれば、デバッグ時のログ出力に使ってください */
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 社員一覧画面に遷移するコントローラーメソッド。
     */
    @GetMapping("/")
    public String index(Model model) {
        Iterable<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "index";
    }

    /**
     * 社員追加画面に遷移するコントローラーメソッド。
     */
    @GetMapping("/insertMain")
    public String insertMain(Model model) {
        // フィールドが全てnullのフォームインスタンスを追加する
        model.addAttribute(CustomerForm.createEmptyForm());
        return "insertMain";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") int userId, Model model){

        Optional<Customer> customer = customerService.findById(userId);
        if (!customer.isPresent()) {
            return "redirect:/";
        }

        model.addAttribute("customerForm", customer.get());
        return "edit";
    }

    @PostMapping("/edit/{userId}")
    public String editComplete(
            @PathVariable("userId") int userId,
            @Validated CustomerForm customerForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        Optional<Customer> customer = customerService.findById(userId);
        customer.ifPresent(c -> {
            Customer req = customerForm.convertToEntity();
            c.setBirthday(req.getBirthday());
            c.setEmail(req.getEmail());
            c.setFirstName(req.getFirstName());
            c.setLastName(req.getLastName());
            customerService.save(c);
        });

        return "redirect:/";
    }


    /**
     * 社員の追加を行うコントローラーメソッド。
     */
    @PostMapping("/insertComplete")
    public String insertComplete(
            @Validated CustomerForm customerForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insertMain";
        }
        // フォームをエンティティに変換
        Customer customer = customerForm.convertToEntity();
        customerService.save(customer);
        return "redirect:/";
    }

    @PostMapping("/remove/{userId}")
    public String remove(@PathVariable("userId") int userId){
        customerService.removeById(userId);
        return "redirect:/";
    }
}
