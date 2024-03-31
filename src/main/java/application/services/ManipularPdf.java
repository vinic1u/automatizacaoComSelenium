package application.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ManipularPdf {

    private String caminhoDoPdf;

    public ManipularPdf(String caminhoDoPdf) {
        this.caminhoDoPdf = caminhoDoPdf;
    }

    public boolean textoEncontrado(String textoParaBuscar){

        boolean textoEncontrado=false;
        try {
            // Carrega o arquivo PDF
            File arquivo = new File(caminhoDoPdf);

            PDDocument document = PDDocument.load(arquivo);
            System.out.println("Arquivo LIDO!");

            // Cria um PDFTextStripper para extrair o texto
            PDFTextStripper stripper = new PDFTextStripper();

            // Obtém o texto do documento
            String text = stripper.getText(document);


            textoEncontrado = text.toLowerCase().contains(textoParaBuscar.toLowerCase());

            document.close();
            arquivo.deleteOnExit();

        } catch (IOException e) {
            System.out.println("Arquivo nao é um PDF");
        }

        return textoEncontrado;
    }

}
