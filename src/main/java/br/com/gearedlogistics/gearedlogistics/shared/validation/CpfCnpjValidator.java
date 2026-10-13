package br.com.gearedlogistics.gearedlogistics.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementação da validação de CPF/CNPJ.
 */
public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    private static final int[] PESOS_CNPJ_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_CNPJ_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        String digitos = value.replaceAll("\\D", "");
        return switch (digitos.length()) {
            case 11 -> isCpfValido(digitos);
            case 14 -> isCnpjValido(digitos);
            default -> false;
        };
    }

    private boolean isCpfValido(String cpf) {
        if (todosDigitosIguais(cpf)) {
            return false;
        }
        for (int posicao = 9; posicao < 11; posicao++) {
            int soma = 0;
            for (int i = 0; i < posicao; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * ((posicao + 1) - i);
            }
            int digito = 11 - (soma % 11);
            if (digito >= 10) {
                digito = 0;
            }
            if (Character.getNumericValue(cpf.charAt(posicao)) != digito) {
                return false;
            }
        }
        return true;
    }

    private boolean isCnpjValido(String cnpj) {
        if (todosDigitosIguais(cnpj)) {
            return false;
        }
        return validaDigitoCnpj(cnpj, PESOS_CNPJ_1, 12)
                && validaDigitoCnpj(cnpj, PESOS_CNPJ_2, 13);
    }

    private boolean validaDigitoCnpj(String cnpj, int[] pesos, int posicaoDigito) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        int digito = resto < 2 ? 0 : 11 - resto;
        return Character.getNumericValue(cnpj.charAt(posicaoDigito)) == digito;
    }

    private boolean todosDigitosIguais(String valor) {
        return valor.chars().distinct().count() == 1;
    }
}
