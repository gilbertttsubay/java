package com.tagihan.Tagihan.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tabel_pln", schema = "PLN")
public class PLN implements Serializable {
    @Id
    @Column(name = "id_pln")
    private String idPln;
    @Column(name = "jumlah_tagihan")
    private int jumlahTagihan;
    @Column(name = "status_tagihan")
    private boolean statusTagihan;
}
