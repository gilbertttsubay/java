package com.tagihan.Tagihan.Model;

import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "ovo")
    private int ovo;
    @Column(name = "no_bpjs")
    private String noBpjs;
    @Column(name = "no_pln")
    private String noPln;
    @Column(name = "no_indihome")
    private String noIndihome;


}
