package com.example.demo.entities;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "searchKeywords")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class searchKeywords {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String keyword;

  @Builder.Default
  @ManyToMany(mappedBy = "searchKeyword")
  private Set<Products> products = new HashSet<>();

  

  public searchKeywords(String newkeyword) {
    if (newkeyword == null || newkeyword.trim().isEmpty()) {
      throw new IllegalArgumentException("Keyword cannot be null or empty");
  }
    this.keyword = newkeyword.trim().toLowerCase();
  }
}
