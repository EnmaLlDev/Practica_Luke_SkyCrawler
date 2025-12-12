import crawler.SWCrawler;
import models.FilmReport;

public class mainApp {
        public static void main(String[] args) {
            SWCrawler swcrawler = new SWCrawler();

            swcrawler.crawlFilm(1)
                    .thenAccept(FilmReport::print)
                    .join();

            System.out.println("Crawler finalizado.");
        }
    }