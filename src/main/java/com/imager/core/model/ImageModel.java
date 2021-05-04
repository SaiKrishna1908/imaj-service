package com.imager.core.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class ImageModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fileName;

  @Lob
  private Byte[] data;


}
