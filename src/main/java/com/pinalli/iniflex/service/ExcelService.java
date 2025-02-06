package com.pinalli.iniflex.service;

import com.pinalli.iniflex.model.Funcionario;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void exportarFuncionarios(List<Funcionario> funcionarios, String filePath) {
        try (var workbook = new XSSFWorkbook();
             var fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Funcionários");

            // Criar cabeçalho
            criarCabecalho(sheet);

            // Preencher dados dos funcionários
            preencherDadosFuncionarios(sheet, workbook, funcionarios);

            // Auto-size das colunas
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escrever no arquivo
            workbook.write(fileOut);
            System.out.println("Exportação concluída: " + filePath);

        } catch (IOException e) {
            System.err.println("Erro ao exportar para Excel: " + e.getMessage());
            throw new RuntimeException("Erro ao exportar para Excel", e);
        }
    }

    private void criarCabecalho(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nome");
        headerRow.createCell(1).setCellValue("Data Nascimento");
        headerRow.createCell(2).setCellValue("Salário");
        headerRow.createCell(3).setCellValue("Função");
    }

    private void preencherDadosFuncionarios(Sheet sheet, XSSFWorkbook workbook, List<Funcionario> funcionarios) {
        CellStyle numberStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        numberStyle.setDataFormat(format.getFormat("#,##0.00"));

        int rowNum = 1;
        for (Funcionario func : funcionarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(func.getNome());
            row.createCell(1).setCellValue(func.getDataNascimento().format(DATE_FORMATTER));

            Cell salarioCell = row.createCell(2);
            salarioCell.setCellValue(func.getSalario().doubleValue());
            salarioCell.setCellStyle(numberStyle);

            row.createCell(3).setCellValue(func.getFuncao());
        }
    }
}
