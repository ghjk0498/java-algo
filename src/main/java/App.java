import implementation.Java1546;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("https://www.acmicpc.net/problem/1546")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://www.google.com")
                    .get();

            Elements elements = document.select(".sampledata");
            List<String> inputs = new ArrayList<>();
            List<String> outputs = new ArrayList<>();
            for (Element element : elements) {
                if (StringUtils.contains(element.id(), "input")) {
                    inputs.add(element.text());
                } else if (StringUtils.contains(element.id(), "output")) {
                    outputs.add(element.text());
                }
            }

            int count = 0;
            PrintStream originalOut = System.out;
            for (int i = 0; i < inputs.size(); i++) {
                String x = inputs.get(i).strip();
                String y = outputs.get(i).strip();

                ByteArrayInputStream bais = new ByteArrayInputStream(x.getBytes());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream newOut = new PrintStream(baos);

                System.setIn(bais);
                System.setOut(newOut);

                Java1546.main(args);
                String yHat = baos.toString().strip();

                if (stringCheck(yHat, y) || errorCheck(yHat, y)) {
                    count += 1;
                }

                bais.close();
                baos.close();
                newOut.close();
            }

            originalOut.printf("%d/%d", count, outputs.size());
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static boolean errorCheck(Object o, Object o2) {
        try {
            double a = Double.parseDouble(o.toString());
            double b = Double.parseDouble(o2.toString());
            double absoluteError = Math.abs(a - b);
            double relativeError = Math.abs(a - b) / Math.abs(a);
            return (absoluteError <= 0.01) || (relativeError <= 0.01);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean stringCheck(String o, String o2) {
        try {
            return StringUtils.equals(o, o2);
        } catch (Exception e) {
            return false;
        }
    }
}