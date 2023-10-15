package com.elias.club.model;

import com.elias.club.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_boleto")
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroDAR;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private Boolean statusPago;

    public String criarBoleto() throws IOException, WriterException {
        byte[] image = new byte[0];
        image = QRCodeGenerator.getQRCodeImage(numeroDAR.toString(),250,250);
        String qrcode = Base64.getEncoder().encodeToString(image);
        return qrcode;
    }
}
