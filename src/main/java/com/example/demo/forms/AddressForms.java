package com.example.demo.forms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddressForms {
  
  private String street;
  private String city;
  private String state;
  private Integer pinCode;

}
