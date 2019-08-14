package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    AdvertService advertService;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CartService cartService;

    // get index page
    @GetMapping(value = {"/"})
    public String index(Model model) {
        //brings products
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);


        return "index";
    }

    // add product to shopping cart.
    @PostMapping(value = {"/product/addToCart"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean addProductToCart(@RequestBody String id) {
        // get current user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user == null)
                return false;

            // get product.
            Product product = productService.findById(Long.parseLong(id));
            Buyer buyer = buyerService.getBuyerByUser(user);

            // make new cart item with the product id.
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setBuyer(buyer);
            cartItem.setQuantity(1);

            cartService.saveCartItem(buyer, cartItem);

        }else{
            return false;
        }
        return true;
    }

    // 404 page
    @GetMapping("/404")
    public String get404() {
        return "404";
    }

    // 403 page
    @GetMapping("/403")
    public String get403(Principal user, Model model) {
        if (user != null) {
            model.addAttribute("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
        } else {
            model.addAttribute("msg", "You do not have permission to access this page!");
        }
        return "403";
    }

    // admin homepage
    @GetMapping(value = {"/admin/dashboard", "/admin"})
    public String adminHomepage() {
        return "/admin/dashboard.html";
    }
}
