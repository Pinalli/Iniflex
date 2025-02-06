package com.pinalli.iniflex;

import com.pinalli.iniflex.service.ExcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.pinalli.iniflex.service.PrincipalFuncionarioService;



@SpringBootApplication
public class IniflexApplication implements CommandLineRunner {

	@Autowired
	private PrincipalFuncionarioService functionarioService;

	@Autowired
	private ExcelService excelService;


    public static void main(String[] args) {
		SpringApplication.run(IniflexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		functionarioService.inicializarFuncionarios();
		functionarioService.listarFuncionarios();
		functionarioService.removerFuncionario("João");
		functionarioService.aplicarAumento();
		functionarioService.listarFuncionariosAgrupadosPorFuncao();
		functionarioService.imprimirAniversariantes();
		functionarioService.imprimirFuncionarioComMaiorIdade() ;
		functionarioService.imprimirListaPorOrdemAlfabetica();
		functionarioService.imprimirTotalSalarios();
		functionarioService.imprimirQtdSalariosMinimos();

		excelService.exportarFuncionarios(functionarioService.listarTodos(), "funcionarios.xlsx");
	}
}