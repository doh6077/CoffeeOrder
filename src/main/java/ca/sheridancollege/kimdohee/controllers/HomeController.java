package ca.sheridancollege.kimdohee.controllers;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ca.sheridancollege.kimdohee.beans.Coffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.kimdohee.beans.CartItem;
import ca.sheridancollege.kimdohee.beans.User;
import ca.sheridancollege.kimdohee.database.DatabaseAccess;

@Controller
public class HomeController {

	@Autowired
	private DatabaseAccess da;
	

	private List<Coffee> coffeeList = new CopyOnWriteArrayList<>();
	private List<CartItem> cartItems = new CopyOnWriteArrayList<>();
	
	
	@GetMapping("/")
	public String index(Model model) {
	    model.addAttribute("book", new Coffee());
	    model.addAttribute("user", new User());
	    model.addAttribute("bookList", da.getBookList());
	    model.addAttribute("cartSize", cartItems.size());
	    return "main";
	}
    @GetMapping("/secure")
    public String secureIndex() {
        return "secure/index";
    }

	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
		da.addUser(email, username, password);
		Long userId = da.findUserAccount(username).getUserId();
	
		return "/register";
	}

    @GetMapping("/login")
    public String login() {
        return "login";
    }
	@PostMapping("/insertBook")
	public String insertBook(Model model, @ModelAttribute Coffee coffee) {
	    da.insertBook(coffee);
	    // Optionally, you can skip adding a new Book to the model unless needed for some specific use case.
	    model.addAttribute("book", new Coffee());
	    model.addAttribute("bookList", da.getBookList());
	    model.addAttribute("cartSize", cartItems.size());
	    return "index";
	}

	

    
	@GetMapping("/editBookById/{id}")
	public String editBookById(Model model, @PathVariable Long id) {
	 
	    Coffee coffee = da.getBookListById(id).get(0);
	    da.deleteBookById(id);
	    model.addAttribute("BookList", da.getBookList());
	    model.addAttribute("book", coffee);
	    model.addAttribute("cartSize", cartItems.size());

	    return "index"; 
	}

	@GetMapping("/deleteBookById/{id}")
	public String deleteBookById(Model model, @PathVariable Long id) {
	    da.deleteBookById(id);
	    model.addAttribute("book", new Coffee());
	    model.addAttribute("bookList", da.getBookList());
	    model.addAttribute("cartSize", cartItems.size());

	    return "index"; // Redirect to the main page after deletion
	}

	@GetMapping("/viewBookById/{id}")
	public String viewBookById(Model model, @PathVariable Long id) {
		 Coffee coffee = da.getBookListById(id).get(0);
		 model.addAttribute("book", coffee);
		 model.addAttribute("cartSize", cartItems.size());
	    return "bookDetail"; 
	}
	
    @PostMapping("books/addToCart/{id}")
    public String addToCart(@PathVariable Long id, Model model) {
    	 model.addAttribute("cartItems", cartItems);
         model.addAttribute("cartSize", cartItems.size());
        Coffee coffee = da.getBookListById(id).get(0);
        CartItem newCartItem = new CartItem();
        newCartItem.setCoffee(coffee);
        newCartItem.setQuantity(1);
        cartItems.add(newCartItem);
        return "cart";
    }

    @GetMapping("/viewCart")
    public String viewCart(Model model) {
    	model.addAttribute("cartItems", cartItems);
        return "cart";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }




}