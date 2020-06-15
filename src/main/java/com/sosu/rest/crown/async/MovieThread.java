package com.sosu.rest.crown.async;

import com.sosu.rest.crown.entitiy.Product;
import com.sosu.rest.crown.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

public class MovieThread implements Runnable {
    private ProductService productService;
    private String genre;

    public MovieThread(ProductService productService, String genre) {
        this.productService = productService;
        this.genre = genre;
    }

    @Override
    public void run() {
        String selected = "https://www.imdb.com/search/title/?title_type=feature&genres=[GENRE]&sort=release_date,desc&explore=genres";
        String[] formatted = genre.split("@");
        selected = selected.replace("[GENRE]", formatted[0].toLowerCase().replace(" ", "-"));
        boolean error = false;
        while (!error) {
            try {
                Document document = Jsoup.connect(selected).get();
                Elements link = document.getElementsByClass("lister-page-next next-page");
                if (CollectionUtils.isEmpty(link)) {
                    error = true;
                } else {
                    selected = "https://www.imdb.com" + link.get(0).attr("href");
                }
                Elements products = document.getElementsByClass("lister-item mode-advanced");
                for (Element movie : products) {
                    Product product = new Product();
                    product.setCategoryId(formatted[1]);
                    Element picture = movie.getElementsByClass("lister-item-image float-left").get(0);
                    product.setImage(picture.getElementsByTag("img").get(0).absUrl("src"));

                    Elements basics = movie.getElementsByClass("lister-item-content");
                    product.setName(basics.get(0).getAllElements().get(3).text());
                    String[] splitted = basics.get(0).getAllElements().get(3).attr("href").split("/");
                    product.setImdbId(splitted[2]);
                    try {
                        product.setYear(Integer.parseInt(basics.get(0).getAllElements().get(4).text().replaceAll("\\D+", "")));
                    } catch (Exception e) {
                        product.setYear(0);
                        product.setName(product.getName() + " " + basics.get(0).getAllElements().get(4).text());
                    }
                    Elements movieDetail = basics.get(0).getElementsByClass("text-muted");
                    Element movieDetailHead = movieDetail.get(1);
                    Element movieDetailDesc = movieDetail.get(2);
                    if (!CollectionUtils.isEmpty(movieDetailHead.getElementsByClass("certificate"))) {
                        product.setCert(movieDetailHead.getElementsByClass("certificate").text());
                    }
                    if (!CollectionUtils.isEmpty(movieDetailHead.getElementsByClass("runtime"))) {
                        product.setMin(Integer.parseInt(movieDetailHead.getElementsByClass("runtime").text()
                                .replace("min", "").trim()));
                    }
                    if (!CollectionUtils.isEmpty(movieDetailHead.getElementsByTag("b"))) {
                        product.setStatus(movieDetailHead.getElementsByTag("b").text());
                    } else {
                        product.setStatus("Completed");
                    }
                    product.setDescription(movieDetailDesc.text());
                    syncr(formatted, product);
                }
            } catch (Exception e) {
                error = true;
            }
        }
    }

    private synchronized void syncr(String[] formatted, Product product) {
        Product selectedProduct = productService.getProductByNameAndYear(product.getName(), product.getYear());
        if (selectedProduct != null) {
            selectedProduct.setCategoryId(selectedProduct.getCategoryId() + ";" + formatted[1]);
            productService.saveOrUpdate(selectedProduct);
        } else {
            productService.saveOrUpdate(product);
        }
    }
}
