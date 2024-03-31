package application.services;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScrapDownloader{

    private String urlDaPagina = "https://campomourao.atende.net/diariooficial/edicao/dataInicial/";
    private String caminhoDosDownload;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public ScrapDownloader(String caminhoDosDownload,LocalDate dataInicio,LocalDate dataFim){
        this.caminhoDosDownload = caminhoDosDownload;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    private String urlComFiltroDeData(){
        StringBuilder stringBuilder = new StringBuilder(this.urlDaPagina);
        stringBuilder.append(this.dataInicio.format(formatter));
        stringBuilder.append("/dataFinal/");
        stringBuilder.append(this.dataFim.format(formatter));

        return stringBuilder.toString();
    }

    public void baixarPdfsDoDia() throws Exception {
        File pastaDeDownloads = new File(this.caminhoDosDownload);

        if(pastaDeDownloads.isDirectory()) {
            WebDriver driver = new ChromeDriver(definirCaminhoDeDownloads());

            driver.get(urlComFiltroDeData());

            Thread.sleep(1500);

            try {
                WebElement enviosDoDia = driver.findElement(By.className("nova_listagem"));
                List<WebElement> pdfEnvios = enviosDoDia.findElements(By.className("info"));

                int pdfIndex = 0;
                for (WebElement envio : pdfEnvios) {
                    pdfIndex++;
                    driver.findElement(
                                    By.xpath("/html/body/div[2]/div[2]/div/div[2]/div[1]/div[2]/div[" + pdfIndex + "]/div[2]/button[2]"))
                            .click();
                    Thread.sleep(2000);
                }
            }
            catch (NoSuchElementException e) {
                System.out.println("Nao possui Envios do dia");
            }
            Thread.sleep(1000);
            driver.quit();

        }else{
            throw new Exception("Caminho de Diretorio para Download Incorreto!");
        }
    }

    private ChromeOptions definirCaminhoDeDownloads(){

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", this.caminhoDosDownload);

        options.setExperimentalOption("prefs", prefs);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }

}
