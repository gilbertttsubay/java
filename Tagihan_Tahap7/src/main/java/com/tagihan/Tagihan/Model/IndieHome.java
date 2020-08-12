package com.tagihan.Tagihan.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tabel_indihome")
public class IndieHome implements Serializable {
    @Id
    @Column(name = "id_indihome")
    private String idIndihome;
    @Column(name = "jumlah_tagihan_internet")
    private int jumlah_tagihan_internet;
    @Column(name = "status_tagihan_internet")
    private boolean status_tagihan_internet;
    @Column(name = "jumlah_tagihan_tv")
    private int jumlah_tagihan_tv;
    @Column(name = "status_tagihan_tv")
    private boolean status_tagihan_tv;
}
