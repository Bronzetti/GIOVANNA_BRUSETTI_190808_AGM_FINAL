package com.example.listapersonagens.dao;

import com.example.listapersonagens.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;//Contador

    public void salvar(Personagem personagemSalvo) {
        personagemSalvo.setId(contadorDeId);
        personagens.add(personagemSalvo);
        atualizaId();
    }

    private void atualizaId() {
        contadorDeId++;
    }

    public void editar(Personagem personagem) {
        Personagem personagemEscolhido = buscaPersonagemId(personagem);
        if (personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemId(Personagem personagem) {
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    public List<Personagem> todos() {

        return new ArrayList<>(personagens);
    }

    public void remove(Personagem personagem){
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if(personagemDevolvido != null){
            personagens.remove(personagemDevolvido);
        }
    }
}
