package com.pinalli.iniflex.service;


import com.pinalli.iniflex.model.Funcionario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Serviço principal para gerenciamento de funcionários.
 * Contém métodos para listagem, remoção, exportação para Excel e outras operações.
 *
 * @author Alberto Rocha Pinalli
 * @since 06/02/2025
 */
@Service
public class PrincipalFuncionarioService {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("1.10");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");

    /**
     * Adiciona todos os funcionários à lista, mantendo a mesma ordem e informações da tabela original.
     */

    public void inicializarFuncionarios() {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14),
                new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5),
                new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19),
                new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31),
                new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8),
                new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24),
                new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2),
                new BigDecimal("2799.93"), "Gerente"));
    }

    /**
     * Remove o funcionário chamado "João" da lista, caso esteja presente.
     */

    public void removerFuncionario(String nome) {
        // Remover o funcionário da lista
        funcionarios.removeIf(f -> f.getNome().equals(nome));

        // Exibir a mensagem informando que o João foi removido
        System.out.println("\nLista de funcionários sem João:");

        // Listar os funcionários restantes, agora sem João
        funcionarios.forEach(f -> System.out.printf("%-10s %-12s R$ %,.2f %-15s%n",
                f.getNome(),
                f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                f.getSalario(),
                f.getFuncao()));
    }

    /**
     * Imprime a lista de todos os funcionários com suas informações formatadas.
     *
     * Regras de formatação:
     * - A data de nascimento é exibida no formato "dd/MM/yyyy".
     * - O salário é formatado com separador de milhar como ponto e decimal como vírgula.
     */
    public void listarFuncionarios() {
        System.out.println("\nLista dos Funcionários:");
        funcionarios.forEach(f -> System.out.printf("%s - %s - R$ %s - %s%n",
                f.getNome(),
                f.getDataNascimento().format(DATE_FORMATTER),
                DECIMAL_FORMATTER.format(f.getSalario()),
                f.getFuncao()));
    }

    /**
     * Aplica um aumento de 10% no salário de todos os funcionários,
     * atualizando a lista com os novos valores.
     */

    public void aplicarAumento() {
        System.out.println("\nLista de funcionarios com aumento de 10%: ");
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(PERCENTUAL_AUMENTO)));
        funcionarios.forEach(f -> System.out.printf("%s - %s - R$ %s - %s%n",
                f.getNome(),
                f.getDataNascimento().format(DATE_FORMATTER),
                DECIMAL_FORMATTER.format(f.getSalario()),
                f.getFuncao()));

    }

    /**
     * Agrupa os funcionários por função em um Map, onde a chave é a função
     * e o valor é a lista de funcionários que exercem essa função.
     */

    public void listarFuncionariosAgrupadosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        /**
         * Imprime os nomes dos funcionários agrupados por função.
         * Formato de saída:
         * Função 1:
         * - Nome funcionário 1
         * - Nome funcionário 2
         * Função 2:
         * - Nome funcionário 3
         */
        System.out.println("\n=== Lista de Funcionários por Função ===");

        funcionariosPorFuncao.forEach((funcao, listaDeFuncionarios) -> {
            System.out.println("------------------------------------------------");
            System.out.printf("Função: %s%n", funcao);
            System.out.println("------------------------------------------------");

            listaDeFuncionarios.forEach(f -> System.out.println("- " + f.getNome()));

            System.out.println(); // Linha em branco para separar funções
        });
    }

    /**
     * Imprime o nome dos funcionários que fazem aniversário em Outubro (10) ou Dezembro (12).
     * Formato da saída:
     * Aniversariantes de Outubro e Dezembro:
     * - Nome funcionário 1 (dd/mm/aa)
     * - Nome funcionário 2 (dd/mm/aa)
     */
    public List<Funcionario> buscarAniversariantes() {
        return funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .collect(Collectors.toList());
    }

    public void imprimirAniversariantes() {
        List<Funcionario> aniversariantes = buscarAniversariantes();
        if (aniversariantes.isEmpty()) {
            System.out.println("\nNenhum funcionário faz aniversário em outubro ou dezembro.");
        } else {
            System.out.println("\n=== Aniversariantes de Outubro e Dezembro: ===");

            aniversariantes.forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento()));
        }
    }

    /**
     * Identifica e imprime o funcionário com a maior idade da lista.
     * Formato da saída:
     * Funcionário com a maior idade
     * Nome: [nome]
     * Idade: [idade] anos
     */

    public void imprimirFuncionarioComMaiorIdade() {
        LocalDate hoje = LocalDate.now();
        Funcionario maiorIdade = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maiorIdade != null) {
            int idade = Period.between(maiorIdade.getDataNascimento(), hoje).getYears();
            System.out.println("\n=== Funcionário com a maior idade ===");
            System.out.println("Nome: " + maiorIdade.getNome());
            System.out.println("Idade: " + idade + " anos");
        } else {
            System.out.println("Nenhum funcionário encontrado.");
        }

    }

    /**
     * Exibe a lista de nomes dos funcionários em ordem alfabética.
     * Formato da saída:
     * 1. [nome]
     * 2. [nome]
     * ...
     */
    public void imprimirListaPorOrdemAlfabetica() {
        System.out.println("\n === Lista de funcionários em ordem alfabética ===");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));
    }

    /**
     * Calcula e exibe o valor total da folha salarial.
     * Formato da saída:
     * Total de salários: R$ XX.XXX,XX
     */
    public void imprimirTotalSalarios() {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n === Total dos salários: === \nR$ " + DECIMAL_FORMATTER.format(totalSalarios));
    }
    /**
     * Imprime a quantidade de salários mínimos que cada funcionário ganha,
     * considerando que o salário mínimo é R$ 1.212,00.
     */

    public void imprimirQtdSalariosMinimos() {
        System.out.println("\n === Quantidade de salários mínimos por funcionário ===");

        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario()
                    .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);

            System.out.println(f.getNome() + ": " + qtdSalarios + " salários mínimos");
        });
    }

    /**
     * Retorna a lista completa de funcionários para exportação.
     */

    public List<Funcionario> listarTodosFuncionarios() {
        return new ArrayList<>(funcionarios);
    }
}
