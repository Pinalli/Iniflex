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

@Service
public class PrincipalFuncionarioService {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("1.10");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");

    /**
     * 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
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
     * 3.2 – Remover o funcionário “João” da lista.
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
     * 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
     * • informação de data deve ser exibido no formato dd/mm/aaaa;
     * • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
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
     * 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
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
     * 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
     */
    public void listarFuncionariosAgrupadosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        /**
         * 3.6 – Imprimir os funcionários, agrupados por função.
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
     * 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
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
            System.out.println("\n=== Lista de Funcionários que fazem aniversário em outubro e dezembro ===");

            aniversariantes.forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento()));
        }
    }

    /**
     * 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
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
     * 3.10 – Imprimir a lista de funcionários por ordem alfabética.
     */
    public List<Funcionario> listarPorOrdemAlfabetica() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    /**
     * 3.11 – Imprimir o total dos salários dos funcionários.
     */
    public void imprimirListaPorOrdemAlfabetica() {
        List<Funcionario> listaOrdenada = listarPorOrdemAlfabetica();
        System.out.println("\nLista de funcionários em ordem alfabética:");
        listaOrdenada.forEach(f -> System.out.println(f.getNome()));
    }

    public BigDecimal calcularTotalSalarios() {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void imprimirTotalSalarios() {
        BigDecimal totalSalarios = calcularTotalSalarios();
        System.out.println("\nTotal dos salários dos funcionários:\nR$ " + DECIMAL_FORMATTER.format(totalSalarios));
    }

    /**
     * 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
     */
    public void imprimirQtdSalariosMinimos() {
        System.out.println("\nQuantidade de salários mínimos por funcionário:");

        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario()
                    .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);

            System.out.println(f.getNome() + ": " + qtdSalarios + " salários mínimos");
        });
    }

    /**
     * Fornece a lista completa de funcionários para o método de exportação.
     */

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }
}
