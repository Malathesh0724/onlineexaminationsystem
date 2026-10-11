package com.Exam.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

	 @NotBlank(message = "Name is required")
	    private String name;

	    @Email(message = "Email is required")
	    @NotBlank(message = "Enter a valid email address")
	    private String email;

	    @NotBlank(message = "Phone number is required")
	    private String phone;

	    @NotBlank(message = "Department is required")
	    private String department;

	    @NotBlank(message = "Password is required")
	    private String password;
}