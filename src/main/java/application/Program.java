package application;

import application.services.EnviarEmailService;
import application.services.ManipularPdf;
import application.services.ScrapDownloader;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.time.LocalDate;

public class Program {
    public static void main(String[] args) throws InterruptedException {

        String caminhoDosDownloads = "C:\\Users\\Pedro\\Desktop\\edicoesCampoMourao";

        ScrapDownloader scrap = new ScrapDownloader(
                caminhoDosDownloads,
                LocalDate.now(),
                LocalDate.now());

        scrap.baixarPdfsDoDia();

        File pastaDosDownloads = new File(caminhoDosDownloads);


        // Variaveis do Ambiente
        Dotenv variaveis = Dotenv.load();

        for (File arquivo: pastaDosDownloads.listFiles()) {

            ManipularPdf pdfManipulavel = new ManipularPdf(arquivo.getAbsolutePath());

            if(pdfManipulavel.textoEncontrado(variaveis.get("valorParaBuscar"))){


                EnviarEmailService.enviarEmail(
                        variaveis.get("emailDestinatario"),
                        arquivo.getAbsolutePath(),
                        "Voce foi chamado, Finalmente!!!",
                        "Segue o anexo",
                        arquivo.getName());
            }
            arquivo.delete();
        }


    }
}
