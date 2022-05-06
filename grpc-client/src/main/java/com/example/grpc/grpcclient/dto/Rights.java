package com.example.grpc.grpcclient.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rights{
  static final long seriableVersionID = 1L;
  private Long id;
  private Long userId;
  private String name;
}
