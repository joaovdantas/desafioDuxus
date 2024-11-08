package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Spy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;

@ExtendWith(MockitoExtension.class)
public class TesteApiService {

    private static final LocalDate data1993 = LocalDate.of(1993, 1, 1);
    private static final LocalDate data1995 = LocalDate.of(1995, 1, 1);

    @Spy
    @InjectMocks
    private ApiService apiService;

    // Mock ou Spy para objetos necessários nos testes
    private DadosParaTesteApiService dadosParaTesteApiService;

    @BeforeEach
    public void init() {
        dadosParaTesteApiService = new DadosParaTesteApiService();
    }

    // Método para fornecer dados de teste para "testTimeDaData"
    private static Object[][] testTimeDaDataParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();

        List<Time> todosOsTimes = dados.getTodosOsTimes();
        Time timeChicagoBullsDe1995 = dados.getTimeChicagoBullsDe1995();
        Time timeDetroidPistonsDe1993 = dados.getTimeDetroidPistonsDe1993();

        return new Object[][]{
            {data1995, todosOsTimes, timeChicagoBullsDe1995},
            {data1993, todosOsTimes, timeDetroidPistonsDe1993}
        };
    }

    @ParameterizedTest
    @MethodSource("testTimeDaDataParams")
    public void testTimeDaData(LocalDate data, List<Time> todosOsTimes, Time esperado) {
        List<String> timeRetornado = apiService.timeDaData(data, todosOsTimes);
        assertEquals(esperado, timeRetornado);
    }

    // Método para fornecer dados de teste para "testIntegranteMaisUsado"
    private static Object[][] testIntegranteMaisUsadoParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        return new Object[][]{
            {data1993, data1995, todosOsTimes, dados.getDenis_rodman()}
        };
    }

    @ParameterizedTest
    @MethodSource("testIntegranteMaisUsadoParams")
    public void testIntegranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Integrante esperado) {
        Integrante integranteRetornado = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, integranteRetornado);
    }

    // Método para fornecer dados de teste para "testTimeMaisComum"
    private static Object[][] testTimeMaisComumParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        List<String> integrantesEsperados = Arrays.asList(
            dados.getDenis_rodman().getNome(),
            dados.getMichael_jordan().getNome(),
            dados.getScottie_pippen().getNome()
        );

        return new Object[][]{
            {data1993, data1995, todosOsTimes, integrantesEsperados}
        };
    }

    @ParameterizedTest
    @MethodSource("testTimeMaisComumParams")
    public void testTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, List<String> esperado) {
        List<String> nomeDosIntegrantesDoTimeMaisComum = apiService.timeMaisComum(dataInicial, dataFinal, todosOsTimes);
        if (nomeDosIntegrantesDoTimeMaisComum != null) {
            nomeDosIntegrantesDoTimeMaisComum.sort(Comparator.naturalOrder());
        }
        assertEquals(esperado, nomeDosIntegrantesDoTimeMaisComum);
    }

    // Método para fornecer dados de teste para "testFuncaoMaisComum"
    private static Object[][] testFuncaoMaisComumParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        return new Object[][]{
            {data1993, data1995, todosOsTimes, "ala"}
        };
    }

    @ParameterizedTest
    @MethodSource("testFuncaoMaisComumParams")
    public void testFuncaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {
        String funcaoMaisComum = apiService.funcaoMaisComum(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, funcaoMaisComum);
    }

    // Método para fornecer dados de teste para "testFranquiaMaisFamosa"
    private static Object[][] testFranquiaMaisFamosaParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        return new Object[][]{
            {data1993, data1995, todosOsTimes, dados.getFranquiaNBA()}
        };
    }

    @ParameterizedTest
    @MethodSource("testFranquiaMaisFamosaParams")
    public void testFranquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, String esperado) {
        String franquiaMaisFamosa = apiService.franquiaMaisFamosa(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, franquiaMaisFamosa);
    }

    // Método para fornecer dados de teste para "testContagemPorFranquia"
    private static Object[][] testContagemPorFranquiaParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put(dados.getFranquiaNBA(), 2L);

        return new Object[][]{
            {data1993, data1995, todosOsTimes, esperado}
        };
    }

    @ParameterizedTest
    @MethodSource("testContagemPorFranquiaParams")
    public void testContagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {
        Map<String, Long> contagemPorFranquia = apiService.contagemPorFranquia(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFranquia);
    }

    // Método para fornecer dados de teste para "testContagemPorFuncao"
    private static Object[][] testContagemPorFuncaoParams() {
        DadosParaTesteApiService dados = new DadosParaTesteApiService();
        List<Time> todosOsTimes = dados.getTodosOsTimes();

        Map<String, Long> esperado = new HashMap<>();
        esperado.put("ala", 2L);
        esperado.put("ala-pivô", 1L);

        return new Object[][]{
            {data1993, data1995, todosOsTimes, esperado}
        };
    }

    @ParameterizedTest
    @MethodSource("testContagemPorFuncaoParams")
    public void testContagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes, Map<String, Long> esperado) {
        Map<String, Long> contagemPorFuncao = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        assertEquals(esperado, contagemPorFuncao);
    }
}
