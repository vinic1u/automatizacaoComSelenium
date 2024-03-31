package application;

import application.services.EnviarEmailService;
import application.services.ManipularPdf;
import application.services.ScrapDownloader;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o caminho para os downloads: ");
        String caminhoDosDownloads = sc.nextLine();

        System.out.print("Digite um texto para ser buscado no PDF: ");
        String textoParaBuscar = sc.nextLine();

        ScrapDownloader scrap = new ScrapDownloader(
                caminhoDosDownloads,
                LocalDate.now(),
                LocalDate.now());

        scrap.baixarPdfsDoDia();

        File pastaDosDownloads = new File(caminhoDosDownloads);

        Dotenv variaveisDeAmbiente = Dotenv.load();


        for (File arquivo: pastaDosDownloads.listFiles()) {
            ManipularPdf pdfManipulavel = new ManipularPdf(arquivo.getAbsolutePath());

            if(pdfManipulavel.textoEncontrado(textoParaBuscar)){
                EnviarEmailService.enviarEmail(
                        variaveisDeAmbiente.get("emailDestinatario"),
                        arquivo.getAbsolutePath(),
                        "Voce foi chamado, Finalmente!!!",
                        "Segue o anexo",
                        arquivo.getName());
            }
        }



    }
}
