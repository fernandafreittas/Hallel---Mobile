package com.example.hallelapp.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESExample {

    // Chave estática definida como a chave fornecida
    private static final byte[] CHAVE_BYTES = {
            (byte)0x7F, (byte)0x15, (byte)0xB8, (byte)0xC9,
            (byte)0x9F, (byte)0x78, (byte)0x7E, (byte)0x0D,
            (byte)0x3D, (byte)0x0B, (byte)0x92, (byte)0x3B,
            (byte)0x4F, (byte)0x82, (byte)0x87, (byte)0xC2
    };

    private static final SecretKeySpec chaveSecreta = new SecretKeySpec(CHAVE_BYTES, "AES");

    public static String criptografar(String texto) throws Exception {
        Cipher cifra = Cipher.getInstance("AES");
        cifra.init(Cipher.ENCRYPT_MODE, chaveSecreta);
        byte[] textoCriptografado = cifra.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoCriptografado);
    }

    public static String descriptografar(String textoCriptografado) throws Exception {
        Cipher cifra = Cipher.getInstance("AES");
        cifra.init(Cipher.DECRYPT_MODE, chaveSecreta);
        byte[] textoDescriptografado = cifra.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(textoDescriptografado);
    }
}
