package com.example.grpc.grpcclient.dto;

import com.example.grpc.grpcclient.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private long id;
  private String name;
  // 標籤
  private String label;
  // 收貨地址經度
  private Double deliveryAddressLon;
  // 收貨地址維度
  private Double deliveryAddressLat;
  // 最新簽到日
  @JsonFormat(pattern = DateTimeUtils.format.NUMERIC_DATE_TIME_FORMAT_WITH_SECOND)
  private String lastSigninDay;
  // 積分
  private Integer score;
  // 權益
  private List<Rights> rights;

}
