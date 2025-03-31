package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.temporario;

import br.com.addson.projetopraticoimplementacaobackend.models.ServidorTemporario;

import java.time.LocalDate;

public record ServidorTemporarioResumo(
        Integer id,
        LocalDate dataAdmissao
) {
    public static ServidorTemporarioResumo fromEntity(ServidorTemporario servidorTemporario) {
        return new ServidorTemporarioResumo(
                servidorTemporario.getId(),
                servidorTemporario.getDataAdmissao()
        );
    }
}
