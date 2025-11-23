package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {

    private String code;

}
