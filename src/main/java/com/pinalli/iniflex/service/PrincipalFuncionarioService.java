package com.pinalli.iniflex.service;


import com.pinalli.iniflex.model.Funcionario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrincipalFuncionarioService {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");

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

    public void removerFuncionario(String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
        System.out.println("\nLista de funcionários sem João:");
        for (Funcionario f : funcionarios) {
            System.out.printf("%-10s %-12s R$ %,.2f %-15s%n",
                    f.getNome(),
                    f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    f.getSalario(),
                    f.getFuncao()
            );
        }
    }

    public void listarFuncionarios() {
        System.out.println("\nLista dos Funcionários:");
        funcionarios.forEach(f -> System.out.printf("%s - %s - R$ %s - %s%n",
                f.getNome(),
                f.getDataNascimento().format(DATE_FORMATTER),
                DECIMAL_FORMATTER.format(f.getSalario()),
                f.getFuncao()));
    }

    public void aplicarAumento() {
        System.out.println("\nLista de funcionarios com aumento de 10%: ");
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));
        funcionarios.forEach(f -> System.out.printf("%s - %s - R$ %s - %s%n",
                f.getNome(),
                f.getDataNascimento().format(DATE_FORMATTER),
                DECIMAL_FORMATTER.format(f.getSalario()),
                f.getFuncao()));

    }

    public void listarFuncionariosAgrupadosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n=== Lista de Funcionários por Função ===");

        funcionariosPorFuncao.forEach((funcao, listaDeFuncionarios) -> {
            System.out.println("------------------------------------------------");
            System.out.printf("Função: %s%n", funcao);
           // System.out.println("------------------------------------------------");

            listaDeFuncionarios.forEach(f -> System.out.printf(
                    "%-10s",
                    f.getNome(),
                    f.getDataNascimento().format(DATE_FORMATTER),
                    DECIMAL_FORMATTER.format(f.getSalario())
            ));
            System.out.println(); // Linha em branco para separar funções
        });
    }


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
            System.out.println("\n=== Lista de Funcionários que fazem aniversário em outubro e dezembro ===");;
            aniversariantes.forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento()));
        }
    }

    public Funcionario buscarMaisVelho() {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new RuntimeException("Nenhum funcionário encontrado."));
    }
    public int calcularIdade(Funcionario funcionario) {
        LocalDate hoje = LocalDate.now();
        return Period.between(funcionario.getDataNascimento(), hoje).getYears();
    }
    public void imprimirFuncionarioMaisVelho() {
        Funcionario maisVelho = buscarMaisVelho();
        int idade = calcularIdade(maisVelho);

        System.out.println("\n=== Funcionário com a maior idade ===");
        System.out.println("Nome: " + maisVelho.getNome());
        System.out.println("Idade: " + idade + " anos");
    }


    public List<Funcionario> listarPorOrdemAlfabetica() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

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

    public Map<String, BigDecimal> calcularQtdSalariosMinimos() {
        return funcionarios.stream()
                .collect(Collectors.toMap(
                        Funcionario::getNome,
                        f -> f.getSalario().divide(SALARIO_MINIMO, 2,
                                java.math.RoundingMode.HALF_UP)
                ));
    }

    public void imprimirQtdSalariosMinimos() {
        Map<String, BigDecimal> qtdSalariosMinimos = calcularQtdSalariosMinimos();
        System.out.println("\nQuantidade de salários mínimos por funcionário:");
        qtdSalariosMinimos.forEach((nome, qtd) ->
                System.out.println(nome + ": " + qtd + " salários mínimos"));
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }
}
