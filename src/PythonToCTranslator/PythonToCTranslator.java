package PythonToCTranslator;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class PythonToCTranslator {

    public static void main(String[] args) {
        // Mostrar un cuadro de diálogo para seleccionar el archivo Python de entrada
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo Python para traducir");
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó ningún archivo.");
            return;
        }

        File inputFile = fileChooser.getSelectedFile();
        String inputFilePath = inputFile.getAbsolutePath();
        String outputFilePath = inputFilePath.replace(".py", ".c");

        try {
            // Leer el código Python del archivo seleccionado
            String pythonCode = readFromFile(inputFilePath);

            // Traducir el código Python a C
            String cCode = translatePythonToC(pythonCode);

            // Guardar el código traducido en un archivo C
            writeToFile(outputFilePath, cCode);

            JOptionPane.showMessageDialog(null, "Traducción completada.\nCódigo C guardado en:\n" + outputFilePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los archivos: " + e.getMessage());
        }
    }

    // Método para leer el contenido de un archivo
    private static String readFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }
        return content.toString();
    }

    // Método para traducir código Python a C (simplificado)
    private static String translatePythonToC(String pythonCode) {
        StringBuilder cCode = new StringBuilder();
        
        // Añadir encabezado en C
        cCode.append("#include <stdio.h>\n");
        cCode.append("int main() {\n");

        // Traducir 'print' de Python a 'printf' en C
        String[] lines = pythonCode.split("\n");
        for (String line : lines) {
            line = line.trim();
            
            // Ejemplo simple: traducir "print" en Python a "printf" en C
            if (line.startsWith("print(") && line.endsWith(")")) {
                String content = line.substring(6, line.length() - 1);
                cCode.append("    printf(").append(content).append(");\n");
            } else {
                // Dejar otras líneas tal como están (esto se puede mejorar)
                cCode.append("    ").append(line).append("\n");
            }
        }
        cCode.append("    return 0;\n");
        cCode.append("}\n");
        return cCode.toString();
    }

    // Método para escribir el contenido en un archivo
    private static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
