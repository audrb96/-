package neonaduri.api.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.api.repository.StoreRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import neonaduri.domain.Store;
import neonaduri.utils.S3Utils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InsertController {

    private final ClassificationRepository classificationRepository;
    private final RegionRepository regionRepository;
    private final SpotRepository spotRepository;
    private final StoreRepository storeRepository;

    private final AmazonS3Client amazonS3Client;

    private final S3Utils s3Utils;

    private static int a = 114;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @GetMapping("/insert")
    @Transactional
    public String insert() throws IOException {
        InsertData insertData = new InsertData(
                "C:\\Users\\SSAFY\\Desktop\\neonaduri\\S07P22A702\\NEONADURI_back\\src\\main\\resources\\data"
        );
        String[] line = null;
        System.out.println("start");
        Classification cls = getClassification();

        while ((line = insertData.nextRead()) != null) {
            Region reg = getRegion(line);
            Spot spot = getSpot(line, cls, reg);
            Spot setSpotImg = setSpotImg(line, spot);
            getStore(setSpotImg, line);
        }

        System.out.println("finish");
        return "good!";
    }

    @Transactional
    public Region getRegion(String[] line) {
        String[] s = line[6].split(" ");

        if (s.length >= 2) {
            line[6] = s[0];
        }
        Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(line[5], line[6], line[7]);

        if (region == null) {
            region = new Region(line[5], line[6], line[7]);
            regionRepository.save(region);
        }
        return region;
    }

    @Transactional
    public Spot getSpot(String[] line, Classification cls, Region reg) throws IOException {
        Spot spot = spotRepository.findSpotBySpotName(line[0]);

        if (spot == null) {
            spot = new Spot(
                    cls.getClassId(),
                    reg.getRegionId(),
                    line[0],
                    Float.parseFloat(line[8]),
                    Float.parseFloat(line[9]),
                    "EA" + (a++)
            );
            return spotRepository.saveAndFlush(spot);
        }
        return spot;
    }

    public void getStore(Spot spot, String[] line) {
        /* Store 저장 */
        Store store = new Store(spot, line[1], line[2], line[3], line[4]);
        log.info(String.valueOf(a));
        storeRepository.save(store);
    }

    public Classification getClassification() {
        return classificationRepository.findClassificationByMdClassAndSmClass("쇼핑", "시장");
    }

    @Transactional
    public Spot setSpotImg(String[] line, Spot spot) throws IOException {
        if (spot.getSpotImage() == null) {
            String url = "https://www.google.com/search?q=" + line[0] + "&tbm=isch";
            Connection cn = Jsoup.connect(url);
            Document doc = cn.get();
            String img = doc.select("img.rg_i.Q4LuWd").attr("data-src");

            URL url1 = new URL(img);
            InputStream is = url1.openStream();
            FileOutputStream fos = new FileOutputStream("C:\\test/" + spot.getSpotId() + ".jpg");

            int b;
            while ((b = is.read()) != -1) {
                fos.write(b);
            }
            fos.close();

            final String fileDir = "C:\\test/" + spot.getSpotId() + ".jpg";
            final String fileValue = "spot/" + "EA" + spot.getSpotId() + ".jpg";
            File file = new File(fileDir);

            amazonS3Client.putObject(new PutObjectRequest(bucket, fileValue, file).withCannedAcl(CannedAccessControlList.PublicRead));
            spot.setSpotImage(fileValue);
            return spotRepository.save(spot);
        }
        return spot;
    }
}