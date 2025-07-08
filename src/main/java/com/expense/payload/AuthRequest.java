// AuthRequest.java
package com.expense.payload;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
