package com.sparta.backend.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.TreeSet;

public class Selenium {
    private WebDriver webDriver;
    private String chromeDriver;
    private String chromeDriverPath;
    public Selenium() {
        this.chromeDriver = "webdriver.chrome.driver";
    //작업 환경이 리눅스인 관계로 ChromeDriverPath의 위치가 다릅니다.
        this.chromeDriverPath = "D:/chromedriver.exe";
        System.setProperty(chromeDriver, chromeDriverPath);
        ChromeOptions option = new ChromeOptions();
        //창 숨김 옵션(활성화시)
        //option.addArguments("headless");
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-dev-shm-usage");
        webDriver = new ChromeDriver(option);
    }
    private String URL;
    public void setURL(String uRL) {
        URL = uRL;
    }
    public void crawling() throws IOException
    { // video title의 중복된 String 값을 제거하기 위해 TreeSet을 적용하였습니다.
        TreeSet<String> set = new TreeSet<String>();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        webDriver.get(URL);
        // 기존 content 사이즈 비교 temp 변수
        int contentSize = 0;
        try {
            while (true) {
                JavascriptExecutor jse = (JavascriptExecutor) webDriver;
                // 너 왜 안되니..
                //jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                jse.executeScript("window.scrollBy(0,2000)", "");
                Thread.sleep(1000);
                // video-title element scraping
                List<WebElement> list = webDriver.findElements(By.className("sc-fNHLbd.gYlvuL"));
                // TreeSet add content (distinct)
                for (WebElement we : list) {
                    set.add(we.findElement(By.className("sc-ixltIz.cmMvLR")).getText());
                }
                // content increase check
                if (contentSize == list.size()) {
                    break;
                } else {
                    contentSize = list.size();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            webDriver.quit();
        } finally {
            for (String s : set) {
                bw.write(s + "\n");
            }
            bw.write(contentSize + " Content Search\n");
            bw.flush();
            bw.close();
            webDriver.quit();
        }
    }


}
