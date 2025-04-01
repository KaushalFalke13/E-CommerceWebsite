package com.example.demo.forms;

import com.example.demo.enums.Status;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersForm {


  private String userid;
  private String name;
  private String email;
  private String password ;
  // private String brandName;
  // private Integer phone_Number;
  @Builder.Default
  private Status status=Status.ACTIVE;
  // private boolean isverified;

}
