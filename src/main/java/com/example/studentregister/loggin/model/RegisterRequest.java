
package com.example.studentregister.loggin.model;

import com.example.studentregister.user.orm.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "نام کاربری الزامی است")
    @Size(min = 3, max = 50, message = "نام کاربری باید بین ۳ تا ۵۰ کاراکتر باشد")
    private String username;

    @NotBlank(message = "کد ملی الزامی است")
    private String nationalCode;

    @NotBlank(message = "رمز عبور الزامی است")
    @Size(min = 8, max = 72, message = "رمز عبور باید بین ۸ تا ۷۲ کاراکتر باشد")
    private String password;

    private String firstName;

    private String lastName;

    @NotNull(message = "انتخاب نقش الزامی است")
    private RoleEnum role;
}
