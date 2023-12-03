package ca.sheridancollege.kimdohee.beans;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@Component 
public class CartItem {
    private Coffee coffee;
    private int quantity;

}
