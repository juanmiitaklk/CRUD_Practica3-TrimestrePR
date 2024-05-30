package es.studium.ProyectoGestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportarExcel 
{
    public void exportarSociosExcel()
    {
        String nombreArchivo = "Socios.xlsx";
        String rutaArchivo = "C:\\Users\\juanj\\OneDrive\\Escritorio\\Prueba Exportacion Excel Java" + nombreArchivo;
        String hoja = "Hoja1";
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(hoja);

        String [] header = new String[]{"ID", "DNI", "Nombre", "Primer Apellido", "Segundo Apellido", "Correo Electronico"};

        Datos datos = new Datos();
        datos.conectar();
        ResultSet resultSet = datos.obtenerSocios();

        CellStyle style = libro.createCellStyle();
        XSSFFont font = libro.createFont();
        font.setBold(true);
        style.setFont(font);

        // Aplicar estilo al encabezado
        XSSFRow headerRow = hoja1.createRow(0);
        for (int j = 0; j < header.length; j++) {
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(style);
            cell.setCellValue(header[j]);
        }

		// Agregar datos
        try {
            int rowNum = 1; // Comenzar desde la segunda fila
            while (resultSet.next()) {
                XSSFRow row = hoja1.createRow(rowNum++);
                for (int i = 1; i <= header.length; i++) {
                    XSSFCell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Escribir en el archivo
        try (FileOutputStream fileOuS = new FileOutputStream(rutaArchivo))
        {
            if (new File(rutaArchivo).exists())
            { // Si el archivo ya existe, se elimina
                new File(rutaArchivo).delete();
                System.out.println("Archivo eliminado");
            }
            // Guardar información en el archivo
            libro.write(fileOuS);
            fileOuS.flush();
            System.out.println("Archivo Creado");
            libro.close();
            
        }
        catch (FileNotFoundException e)
        {
            System.out.println("El archivo no se encuentra o está en uso...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

	public void exportarPistasExcel() 
	{
		String nombreArchivo = "Pistas.xlsx";
        String rutaArchivo = "C:\\Users\\juanj\\OneDrive\\Escritorio\\Pistas Exportacion Excel Java" + nombreArchivo;
        String hoja = "Hoja1";
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(hoja);

        String [] header = new String[]{"ID", "Nombre", "Zona", "Tipo"};

        Datos datos = new Datos();
        datos.conectar();
        ResultSet resultSet = datos.obtenerPistas();

        CellStyle style = libro.createCellStyle();
        XSSFFont font = libro.createFont();
        font.setBold(true);
        style.setFont(font);

        // Aplicar estilo al encabezado
        XSSFRow headerRow = hoja1.createRow(0);
        for (int j = 0; j < header.length; j++) {
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(style);
            cell.setCellValue(header[j]);
        }

		// Agregar datos
        try {
            int rowNum = 1; // Comenzar desde la segunda fila
            while (resultSet.next()) {
                XSSFRow row = hoja1.createRow(rowNum++);
                for (int i = 1; i <= header.length; i++) {
                    XSSFCell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Escribir en el archivo
        try (FileOutputStream fileOuS = new FileOutputStream(rutaArchivo))
        {
            if (new File(rutaArchivo).exists())
            { // Si el archivo ya existe, se elimina
                new File(rutaArchivo).delete();
                System.out.println("Archivo eliminado");
            }
            // Guardar información en el archivo
            libro.write(fileOuS);
            fileOuS.flush();
            System.out.println("Archivo Creado");
            libro.close();
            
        }
        catch (FileNotFoundException e)
        {
            System.out.println("El archivo no se encuentra o está en uso...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		
	}

	public void exportarReservasExcel() 
	{
		String nombreArchivo = "Pistas.xlsx";
        String rutaArchivo = "C:\\Users\\juanj\\OneDrive\\Escritorio\\Pistas Exportacion Excel Java" + nombreArchivo;
        String hoja = "Hoja1";
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(hoja);

        String [] header = new String[]{"ID", "Precio", "Fecha", "Socio", "Pista"};

        Datos datos = new Datos();
        datos.conectar();
        ResultSet resultSet = datos.obtenerReservas();

        CellStyle style = libro.createCellStyle();
        XSSFFont font = libro.createFont();
        font.setBold(true);
        style.setFont(font);

        // Aplicar estilo al encabezado
        XSSFRow headerRow = hoja1.createRow(0);
        for (int j = 0; j < header.length; j++) {
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(style);
            cell.setCellValue(header[j]);
        }

		// Agregar datos
        try {
            int rowNum = 1; // Comenzar desde la segunda fila
            while (resultSet.next()) {
                XSSFRow row = hoja1.createRow(rowNum++);
                for (int i = 1; i <= header.length; i++) {
                    XSSFCell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Escribir en el archivo
        try (FileOutputStream fileOuS = new FileOutputStream(rutaArchivo))
        {
            if (new File(rutaArchivo).exists())
            { // Si el archivo ya existe, se elimina
                new File(rutaArchivo).delete();
                System.out.println("Archivo eliminado");
            }
            // Guardar información en el archivo
            libro.write(fileOuS);
            fileOuS.flush();
            System.out.println("Archivo Creado");
            libro.close();
            
        }
        catch (FileNotFoundException e)
        {
            System.out.println("El archivo no se encuentra o está en uso...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		
	}
}
