package es.enxenio.sife1701.model.usuario.profesor;

import es.enxenio.sife1701.controller.custom.ProfesorAdminDTO;
import es.enxenio.sife1701.controller.custom.filter.ProfesorAdminFilter;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.UsuarioService;
import es.enxenio.sife1701.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by jlosa on 18/10/2017.
 */
@Component
public class ProfesorExcel {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    public XSSFWorkbook generarProfesores(Boolean activos, Integer mes, Integer ano, Locale locale) throws Exception {
        String[] titles = {
            messageSource.getMessage("excel.creditos.nombre", null, locale),
            messageSource.getMessage("excel.creditos.email", null, locale),
            messageSource.getMessage("excel.creditos.skype", null, locale),
            messageSource.getMessage("excel.creditos.fecharegistro", null, locale),
            messageSource.getMessage("excel.creditos.claseimpartida", null, locale)
        };

        ProfesorAdminFilter filter = new ProfesorAdminFilter();
        filter.setSortProperty("apellidos");
        filter.setSortDirection(Sort.Direction.ASC);
        filter.setRol(Rol.ROLE_PROFESOR);
        filter.setActivos(activos);
        filter.setMes(mes);
        filter.setAno(ano);
        List<ProfesorAdminDTO> profesores = usuarioService.findAllProfesorAdmin(filter).getContent();

        XSSFWorkbook wb = new XSSFWorkbook();
        wb.setForceFormulaRecalculation(true);

        Map<String, CellStyle> styles = ExcelUtil.createStyles(wb);

        Sheet sheet = wb.createSheet(messageSource.getMessage("excel.creditos.profesores", null, locale));
        sheet.setForceFormulaRecalculation(true);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(false);
        int rownum = 0;

        // Título
        Row titleRow = sheet.createRow(rownum++);
        titleRow.setHeightInPoints(35);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(messageSource.getMessage("excel.creditos.title", null, locale));
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$E$1"));

        Row infoRow1 = sheet.createRow(rownum++);
        // Mes
        if (mes != null) {
            DateFormat formatter = new SimpleDateFormat("MMMM", locale);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(Calendar.MONTH, mes - 1);
            Cell mesCell = infoRow1.createCell(4);
            mesCell.setCellValue(messageSource.getMessage("excel.creditos.mes", null, locale) + ": " + formatter.format(calendar.getTime()));
        }

        Row infoRow2 = sheet.createRow(rownum++);
        // Fecha del informe
        Cell fechaCell = infoRow2.createCell(0);
        fechaCell.setCellValue(messageSource.getMessage("excel.creditos.fecha", null, locale) + ": " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        // Año
        if (ano != null) {
            Cell anoCell = infoRow2.createCell(4);
            anoCell.setCellValue(messageSource.getMessage("excel.creditos.ano", null, locale) + ": " + ano);
        }

        rownum++;

        // Cabeceras
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rowsAntesDeResultados = rownum;
        for (int i = 0; i < profesores.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(styles.get("cell"));
            }
        }

        // Contenido de la tabla y total
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

        for (int j = 4; j < 5; j++) {
            cell = sumRow.createCell(j);
            int datosMasUno = profesores.size() + rowsAntesDeResultados;
            String ref = (char) ('A' + j) + "6:" + (char) ('A' + j) + datosMasUno;
            cell.setCellFormula("SUM(" + ref + ")");
            cell.setCellStyle(styles.get("formula"));
        }

        int q = rowsAntesDeResultados;
        for (ProfesorAdminDTO profesor : profesores) {
            Row row = sheet.getRow(q);
            String nombre = StringUtils.isNotEmpty(profesor.getApellidos()) ? profesor.getApellidos() + ", " + profesor.getNombre() : profesor.getNombre();
            row.getCell(0).setCellValue(nombre);
            row.getCell(1).setCellValue(profesor.getEmail());
            row.getCell(2).setCellValue(profesor.getSkype());
            row.getCell(3).setCellValue(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(profesor.getFechaRegistro()));
            row.getCell(4).setCellValue(profesor.getClasesImpartidas());
            q++;
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 30 * 256); //30 characters wide
        sheet.setColumnWidth(1, 40 * 256); //30 characters wide
        sheet.setColumnWidth(2, 30 * 256); //30 characters wide
        sheet.setColumnWidth(3, 20 * 256); //30 characters wide
        for (int i = 4; i < 5; i++) {
            sheet.setColumnWidth(i, 15 * 256);  //15 characters wide
        }

        return wb;
    }
}
