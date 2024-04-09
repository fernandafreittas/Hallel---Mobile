package com.example.hallelapp.payload.resposta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroResponse {

    public String token;
    public Object objeto;

}
