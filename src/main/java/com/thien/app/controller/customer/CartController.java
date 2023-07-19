package com.thien.app.controller.customer;

import com.thien.app.dto.CartItemDto;
import com.thien.app.entity.CartItem;
import com.thien.app.entity.User;
import com.thien.app.service.BookService;
import com.thien.app.service.CartItemService;
import com.thien.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer/cart")
public class CartController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ModelAndView cart( Model model){
        //get list cartItem
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        List<CartItem> cartItemList = cartItemService.getCartItemByUserId(user.getId());

        //get list books
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for(int i=0;i<cartItemList.size();i++){
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setUserid(user.getId());
            cartItemDto.setBook(bookService.getBookById(cartItemList.get(i).getBookId()));
            cartItemDto.setQuantity(cartItemList.get(i).getQuantity());
            cartItemDtoList.add(cartItemDto);
        }
        model.addAttribute("cartItemList", cartItemDtoList);
        return new ModelAndView("cart.html", model.asMap());
    }
}
