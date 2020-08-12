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
@Table(name = "tabel_bpjs2")
public class BPJSCLass implements Serializable {

    @Id
    @Column(name = "id_bpjs")
    private String idBpjs;
    @Column(name = "jumlah_tagihan")
    private int jumlahTagihan;
    @Column(name = "status_tagihan")
    private Boolean statusTagihan;
}
