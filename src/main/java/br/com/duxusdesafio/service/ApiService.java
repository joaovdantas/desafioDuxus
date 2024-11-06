package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;

@Service
public class ApiService {
	//Vai retornar uma lista com os nomes dos integrantes do time daquela data
	public List<Integrante> timeDaData(LocalDate data, List<Time> todosOsTimes) {
		List<Integrante> integrantes = new ArrayList<>();/* Instanciando uma lista dos integrantes */
		for (Time time : todosOsTimes) { /* verificando todos os times na lista */
			if (time.getData().isEqual(data)) {/* Verificando se a data digitada é a mesma do time */
				for (ComposicaoTime composicao : time.getComposicaoTime()) {/* busquei acessar o atributo integrante que ta no obj Composiçao */
					integrantes.add(
							composicao.getIntegrante()); /* Adicionando o atributo integrante à lista de integrantes */

				}
			}
		}
		return integrantes; // Retornando o meu metodo que é uma lista com os integrantes do time daquela data
							
	}

	// Retornar o integrante que tiver presente na maior quantidade de times dentro do período
	
	public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		HashMap<Integrante,Integer> contagem = new HashMap<>();
		for (Time time : todosOsTimes) {
			 if (time.getData().isAfter(dataInicial) && time.getData().isBefore(dataFinal)) {
				 for (ComposicaoTime composicao : time.getComposicaoTime()) {/* Acessando todos obj retornados pelo getComposicaoTime e alocando no composicao*/
		                Integrante jogador = composicao.getIntegrante();
		               contagem.put(jogador, contagem.getOrDefault(jogador,0) + 1); /* Na contagem está adicionando o jogador partindo do zero */
		             
				 }
			 }
		}
        Integrante jogadorMaispresente = null; //Criando variavel do tipo Integrante pra verificar o jogador, iniciado com null porque seu tipo tem que armazenar o jogador obj e nao o numero.

        for (Map.Entry<Integrante, Integer > entry : contagem.entrySet()) {// no mapa estou acessando a chave e o valor com o metodo Entryset que faz parte do hashmap
            if(entry.getValue()>0){//se qualquer valor for atribuito a contagem que no caso é 0. entao toda entrando sendo maior q o padrao  fara.
                jogadorMaispresente = entry.getKey(); //variavel instanciada recebe o jogador ou seja a key.
            }
        }
        return jogadorMaispresente;//retornando o jogador mais presente
	}

	/**
	 * Vai retornar uma lista com os nomes dos integrantes do time mais comum dentro
	 * do período
	 */
	public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		// TODO Implementar método seguindo as instruções!
		return null;
	}

	/**
	 * Vai retornar a função mais comum nos times dentro do período
	 */
	public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		// TODO Implementar método seguindo as instruções!
		return null;
	}

	/**
	 * Vai retornar o nome da Franquia mais comum nos times dentro do período
	 */
	public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		// TODO Implementar método seguindo as instruções!
		return null;
	}

	/**
	 * Vai retornar o nome da Franquia mais comum nos times dentro do período
	 */
	public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		// TODO Implementar método seguindo as instruções!
		return null;
	}

	/**
	 * Vai retornar o número (quantidade) de Funções dentro do período
	 */
	public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		// TODO Implementar método seguindo as instruções!
		return null;
	}

}