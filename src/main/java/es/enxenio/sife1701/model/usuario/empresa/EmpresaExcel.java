package es.enxenio.sife1701.model.usuario.empresa;

import es.enxenio.sife1701.controller.custom.EmpresaDTO;
import es.enxenio.sife1701.model.usuario.UsuarioRepository;
import es.enxenio.sife1701.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jlosa on 18/10/2017.
 */
@Component
public class EmpresaExcel {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MessageSource messageSource;

    public XSSFWorkbook generarEmpresas(Locale locale) throws Exception {
        String[] titles = {
            messageSource.getMessage("excel.creditos.nombre", null, locale),
            messageSource.getMessage("excel.creditos.cifnif", null, locale),
            messageSource.getMessage("excel.creditos.email", null, locale),
            messageSource.getMessage("excel.creditos.fecharegistro", null, locale),
            messageSource.getMessage("excel.creditos.tipo.asignados", null, locale),
            messageSource.getMessage("excel.creditos.tipo.distribuidos", null, locale),
            messageSource.getMessage("excel.creditos.tipo.disponibles", null, locale),
            messageSource.getMessage("excel.creditos.tipo.consumidos", null, locale)
        };

        List<EmpresaDTO> empresas = usuarioRepository.getEmpresasDTO();

        XSSFWorkbook wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = ExcelUtil.createStyles(wb);

        Sheet sheet = wb.createSheet(messageSource.getMessage("excel.creditos.empresas", null, locale));
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(false);
        int rownum = 0;

        //title row
        Row titleRow = sheet.createRow(rownum++);
        titleRow.setHeightInPoints(35);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(messageSource.getMessage("excel.creditos.title", null, locale));
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));

        rownum++;

        Row infoRow = sheet.createRow(rownum++);
        Cell fechaCell = infoRow.createCell(0);
        fechaCell.setCellValue(messageSource.getMessage("excel.creditos.fecha", null, locale) + ": " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

        rownum++;

        //header row
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rowsAntesDeResultados = rownum;
        for (int i = 0; i < empresas.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(styles.get("cell"));
            }
        }

        //row with totals below
        Row sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(25);
        Cell cell;
        cell = sumRow.createCell(0);
        cell.setCellValue(messageSource.getMessage("excel.creditos.totales", null, locale));
        cell.setCellStyle(styles.get("formula"));

        for (int j = 1; j < 4; j++) {
            cell = sumRow.createCell(j);
            cell.setCellStyle(styles.get("formula"));
        }

        for (int j = 4; j < 8; j++) {
            cell = sumRow.createCell(j);
            int datosMasUno = empresas.size() + rowsAntesDeResultados;
            String ref = (char)('A' + j) + "5:" + (char)('A' + j) + String.valueOf(datosMasUno);
            cell.setCellFormula("SUM(" + ref + ")");
            cell.setCellStyle(styles.get("formula"));
        }

        int q = 0;
        q = rowsAntesDeResultados;
        for (EmpresaDTO empresa : empresas) {
            Row row = sheet.getRow(q);
            row.getCell(0).setCellValue(empresa.getNombre());
            row.getCell(1).setCellValue(empresa.getNif());
            row.getCell(2).setCellValue(empresa.getEmail());
            row.getCell(3).setCellValue(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(empresa.getFechaRegistro()));
            row.getCell(4).setCellValue(empresa.getCreditosTotales());
            row.getCell(5).setCellValue(empresa.getCreditosDistribuidos());
            row.getCell(6).setCellValue(empresa.getCreditosDisponibles());
            row.getCell(7).setCellValue(empresa.getCreditosConsumidos());
            q++;
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 30*256); //30 characters wide
        sheet.setColumnWidth(1, 15*256); //30 characters wide
        sheet.setColumnWidth(2, 40*256); //30 characters wide
        sheet.setColumnWidth(3, 20*256); //30 characters wide
        for (int i = 4; i < 8; i++) {
            sheet.setColumnWidth(i, 15*256);  //15 characters wide
        }

        return wb;
    }

}
