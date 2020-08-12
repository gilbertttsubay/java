package com.tagihan.Tagihan.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bank implements Serializable {
    private String userName;
    private String idDituju;
    private int noAtm;
    private int noRekTujuan;
    private int totalTransfer;
}
