package application.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
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
            PDDocument document = PDDocument.load(new File(caminhoDoPdf));

            // Cria um PDFTextStripper para extrair o texto
            PDFTextStripper stripper = new PDFTextStripper();

            // Obtém o texto do documento
            String text = stripper.getText(document);

            textoEncontrado = text.toLowerCase().contains(textoParaBuscar.toLowerCase());

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textoEncontrado;
    }
}
