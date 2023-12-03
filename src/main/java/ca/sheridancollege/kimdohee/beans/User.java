package ca.sheridancollege.kimdohee.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
	private Long userId;
	private String userName;
	@NonNull
	private String password;
	@NonNull
	private String email;
}
