package ca.sheridancollege.kimdohee.beans;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@Component 
public class Coffee {
	@Id
	
	public long id; 
	public String name;
	public CoffeeSize size;
	public long price;
	public String description;

	}
