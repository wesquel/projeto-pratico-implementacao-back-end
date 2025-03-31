package br.com.addson.projetopraticoimplementacaobackend.dtos.servidor.efetivo;

import br.com.addson.projetopraticoimplementacaobackend.models.ServidorEfetivo;

public record ServidorEfetivoResumo(
        Integer id,
        String matricula
) {
    public static ServidorEfetivoResumo fromEntity(ServidorEfetivo servidorEfetivo) {
        return new ServidorEfetivoResumo(
                servidorEfetivo.getId(),
                servidorEfetivo.getMatricula()
        );
    }
}
