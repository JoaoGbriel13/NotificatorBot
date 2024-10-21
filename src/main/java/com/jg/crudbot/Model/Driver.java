package com.jg.crudbot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private String nome;
    private String data;
    private String email;
    private String endereco;
    private String iracingId;
    private String tamanhoCamisa;
}
