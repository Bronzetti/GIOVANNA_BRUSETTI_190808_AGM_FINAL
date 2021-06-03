


package com.example.listapersonagens.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagens.R;
import com.example.listapersonagens.dao.PersonagemDAO;
import com.example.listapersonagens.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listapersonagens.ui.activities.ContantesActivities.CHAVE_PERSONAGEM;


public class ListaPersonagemActivity extends AppCompatActivity {

  public static final String TITULO_APPBAR = "Agenda";  //criação da classe Main
  private final PersonagemDAO dao = new PersonagemDAO();
  private ArrayAdapter<Personagem> adapter; //conjunto de comandos

  @Override
  protected void onCreate(Bundle savedInstancesState) {
    super.onCreate(savedInstancesState);
    setContentView(R.layout.activity_lista_personagem);
    setTitle(TITULO_APPBAR); //título ao entrar na lista de personagens
    configuraFabNovoPersonagem();
    configuraLista();
  }

  private void configuraFabNovoPersonagem() {
    FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add); //botão que leva da lista para o formulário
    botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() { //chamar ação do botão
      @Override
      public void onClick(View v) { //toda vez que apertar o fab irá direto para o formulário
        abreFormulario();
      }
    });
  }

  private void abreFormulario() {
    startActivity(new Intent(this, FormularioPersonagemActivity.class));
  }

  @Override
  protected void onResume() {
    super.onResume();
    atualizaPersonagem();
  }

  private void atualizaPersonagem() {
    adapter.clear();
    adapter.addAll(dao.todos());
  }

  private void remove(Personagem personagem){
    dao.remove(personagem);
    adapter.remove(personagem);
   }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    //menu.add("Remover");
    getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);
  }

  @Override
  public boolean onContextItemSelected(@NonNull MenuItem item) {
    configuraMenu(item);
    return super.onContextItemSelected(item);
  }

  private void configuraMenu(@NonNull MenuItem item) {
    int itemId = item.getItemId();
    if(itemId == R.id.activity_lista_personagem_menu_remover) {
      new AlertDialog.Builder(this)
              .setTitle("Removendo personagem")
              .setMessage("Tem certeza que deseja remover?")
              .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                  Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                  remove(personagemEscolhido);
                }
              })
              .setNegativeButton("Não", null)
              .show();
    }
  }

  private void configuraLista() {
    ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem); //pega a lista através do id e identificação no xml
    //final List<Personagem> personagens = dao.todos();
    //listaDePersonagens(listaDePersonagens,personagens); //lista de personagens passada
    listaDePersonagens(listaDePersonagens); //configuração da lista com listagem necessária
    configuraItemPorClique(listaDePersonagens); //configuração item por clique
    registerForContextMenu(listaDePersonagens);
  }

  private void configuraItemPorClique(ListView listaDePersonagens) {
    listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {//seleciona personagem
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) { //superclasse onItemClick
        Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
        abreFormularioEditar(personagemEscolhido);
      }
    });
  }

  private void abreFormularioEditar(Personagem personagemEscolhido) {
    Intent vaiParaoFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);  //intent para abrir meu formulário
    vaiParaoFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);//grava o nome ao clicar nele
    startActivity(vaiParaoFormulario);
  }

  private void listaDePersonagens(ListView listaDePersonagens) {
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    listaDePersonagens.setAdapter(adapter);
  }
}