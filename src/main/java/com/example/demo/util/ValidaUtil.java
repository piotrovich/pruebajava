package com.example.demo.util;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ValidaUtil {

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    private static String regexPatternMail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static String regexPatternPass = "[A-Z]{1}[a-z]{4}[0-9]{2}";

    public static boolean validaEmail(String emailAddress) {
        return Pattern.compile(regexPatternMail)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean validaPass(String pass){
        return Pattern.compile(regexPatternPass)
                .matcher(pass)
                .matches();
    }

    public boolean validaEmailDb(String correo) {

        List<Usuario> usuario = iUsuarioRepository.findAll();
        for (Usuario valida : usuario){
            if (valida.getCorreo().equalsIgnoreCase(correo)){
                return true;
            }
        }
        return false;
    }
}
