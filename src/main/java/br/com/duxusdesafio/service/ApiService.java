package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;

@Service
public class ApiService {

	//Vai retornar uma lista com os nomes dos integrantes do time daquela data

	public List<String> timeDaData(LocalDate data, List<Time> todosOsTimes) {
		List<String> jogadores = new ArrayList<>();/* Instanciando uma lista dos integrantes */
		for (Time time : todosOsTimes) { /* verificando todos os times na lista */
			if (time.getData().isEqual(data)) {/* Verificando se a data digitada é a mesma do time */
				for (ComposicaoTime composicao : time.getComposicaoTime()) {/* busquei acessar o atributo integrante que ta no obj Composiçao */
					jogadores.add(composicao.getIntegrante().getNome()); /* Adicionando o atributo nome dentro da lista de composição a lista jogadores */

				}
				return jogadores;
			}
		}
		return Collections.emptyList();

	}

	// Retornar o integrante que tiver presente na maior quantidade de times dentro do período

	public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		HashMap<Integrante, Integer> contagem = new HashMap<>();
		for (Time time : todosOsTimes) {
			if (!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
				for (ComposicaoTime composicao : time.getComposicaoTime()) {/* Acessando todos obj retornados pelo getComposicaoTime e alocando no composicao*/
					Integrante jogador = composicao.getIntegrante();
					contagem.put(jogador, contagem.getOrDefault(jogador, 0) + 1); /* Na contagem está adicionando o jogador partindo do zero */
				}
			}
		}
		Integrante jogadorMaispresente = null; //Criando variavel do tipo Integrante pra verificar o jogador, iniciado com null porque seu tipo tem que armazenar o jogador obj e nao o numero.
		int jogadorContagem = 0;

		for (Map.Entry<Integrante, Integer> entry : contagem.entrySet()) {// no mapa estou acessando a chave e o valor com o metodo Entryset que faz parte do hashmap
			if (entry.getValue() > jogadorContagem) {//se qualquer valor for atribuito a contagem que no caso é 0. entao toda entrando sendo maior q o padrao  fara.
				jogadorMaispresente = entry.getKey();//variavel instanciada recebe o jogador ou seja a key e atualiza o jogador mais presente
				jogadorContagem = entry.getValue(); // atualizando a contagem de jogadores mais presente
			}
		}
		return jogadorMaispresente;//retornando o jogador mais presente
	}

	//Vai retornar uma lista com os nomes dos integrantes do time mais comum dentro do período

	public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		//verficando o sistema caso seja nulo ou vazio as informações
		if (todosOsTimes == null || todosOsTimes.isEmpty()) {
			return Collections.emptyList(); // Retorna lista vazia se não houver times
		}

		HashMap<Time, Integer> contagemT = new HashMap<>();

		for (Time time : todosOsTimes) {
			if (!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
				contagemT.put(time, contagemT.getOrDefault(time, 0) + 1);
			}
		}

		if (contagemT.isEmpty()) {
			return Collections.emptyList(); // Retorna lista vazia se nenhum time foi encontrado
		}

		Time timeMaisComum = null; //Criando variavel pra verificar o jogador, iniciado com null
		int cont = 0;

		for (Map.Entry<Time, Integer> entry : contagemT.entrySet()) {// no mapa estou acessando a chave e o valor com o metodo Entryset que faz parte do hashmap
			if (entry.getValue() > cont) {//se qualquer valor for atribuito a cont que no caso é 0. entao toda entrando sendo maior..
				timeMaisComum = entry.getKey();//variavel instanciada recebe o time ou seja a key e atualiza o time mais presente
				cont = entry.getValue(); // atualizando a contagem de times mais comum
			}
		}

		List<String> nomesJogadores; // Coleta os nomes em uma lista por padrao
        assert timeMaisComum != null;
        nomesJogadores = timeMaisComum.getComposicaoTime().stream()//usando o metodo strem para mapear a composicaoTime
                .map(comp -> comp.getIntegrante().getNome()) // Mapeia para o nome dos jogadores
                .collect(Collectors.toList());

        return nomesJogadores;
	}

	//Vai retornar a função mais comum nos times dentro do período

	public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {

		HashMap<String, Integer> funcao = new HashMap<>();


		for (Time time : todosOsTimes) {
			if (!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
				for (ComposicaoTime composicaoFuncao : time.getComposicaoTime()) {
					if (composicaoFuncao.getIntegrante() != null && composicaoFuncao.getIntegrante().getFuncao() != null) {
						String funcaoDoJogador = composicaoFuncao.getIntegrante().getFuncao(); // Strng por ser o metodo String
						funcao.put(funcaoDoJogador, funcao.getOrDefault(funcaoDoJogador, 0) + 1);
					}
				}
			}
		}
		return funcao.entrySet().stream()
				.max(Map.Entry.comparingByValue()) //busca a função que apareceu mais vezes
				.map(Map.Entry::getKey) // retorna a chave key do que o parametro acima busca
				.orElse("Vazio");// Valor padrão caso não haja valor


	}

	// Vai retornar o nome da Franquia mais comum nos times dentro do período

	public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {

		if (todosOsTimes == null || todosOsTimes.isEmpty()) {
			return null; //  Retorna nulo
		}

		HashMap<String, Integer> franquias = new HashMap<>();

		for (Time time : todosOsTimes) {
			if (time.getData().isAfter(dataInicial) && time.getData().isBefore(dataFinal)) {
				for (ComposicaoTime composicao : time.getComposicaoTime()) {
					String franquia = composicao.getIntegrante().getFranquia();
					franquias.put(franquia, franquias.getOrDefault(franquia, 0) + 1);
				}
			}
		}

		if (franquias.isEmpty()) {
			return null; // Retorna nulo caso vazio
		}

		String franquiaMaisComum = null;
		int franquiaContagem = 0;

		for (Map.Entry<String, Integer> entry : franquias.entrySet()) {// no mapa estou acessando a chave e o valor com o metodo Entryset que faz parte do hashmap
			if (entry.getValue() > franquiaContagem) {//se qualquer valor for atribuito a framquiaContagem que no caso é 0. entao toda entrando sendo maior..
				franquiaMaisComum = entry.getKey();//variavel instanciada recebe a franquia mais comum ou seja a key e atualiza
				franquiaContagem = entry.getValue(); // atualizando a contagem de times mais comum
			}
		}
		return franquiaMaisComum;
	}

	// Vai retornar o nome da Franquia mais comum nos times dentro do período

	public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {

		if (todosOsTimes == null || todosOsTimes.isEmpty()) {
			return Collections.emptyMap(); // Retorna nulo
		}

		Map<String, Long> contFranquias = todosOsTimes.stream()//.stream para usar os proximos metodos
				.filter(time -> !time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) // .filter para filtrar
				.flatMap(time -> time.getComposicaoTime().stream()) // Achata a lista de composição do time
				.map(composicaoTime -> composicaoTime.getIntegrante().getFranquia()) //BUSCANDO Franquia por jogador
				.collect(Collectors.groupingBy(franquia -> franquia, Collectors.counting())); // passando para as franquia a contagem


		return contFranquias.entrySet().stream()// verificando em contFranquias a maior
				.max(Map.Entry.comparingByValue()) // Encontra a franquia com maior número de jogadores
				.map(entry -> {
					Map<String, Long> resulFranquias = new HashMap<>();// Criando um novo mapa com a franquia mais comum
					resulFranquias.put(entry.getKey(), entry.getValue()); // adicionando  a KEY E VALUE no resul
					return resulFranquias;
				}).orElse(Collections.emptyMap()); // .orElse significa CASO NÃO! neste caso me retorna o um map vazio

	}

	// Vai retornar o número (quantidade) de Funções dentro do período

	public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
		return todosOsTimes.stream()
				.filter(time -> (dataInicial == null || !time.getData().isBefore(dataInicial)) &&
						(dataFinal == null || !time.getData().isAfter(dataFinal)))
				.flatMap(time -> time.getComposicaoTime().stream())
				.map(composicao -> composicao.getIntegrante().getFuncao())
				.collect(Collectors.groupingBy(funcao -> funcao, Collectors.counting()));
	}

}