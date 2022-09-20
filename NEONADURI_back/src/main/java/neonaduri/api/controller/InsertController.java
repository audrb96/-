package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.api.repository.StoreRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import neonaduri.domain.Store;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class InsertController {

    private final ClassificationRepository classificationRepository;
    private final RegionRepository regionRepository;
    private final SpotRepository spotRepository;
    private final StoreRepository storeRepository;

    @GetMapping("/insert")
    @Transactional
    public String insert() throws IOException {
        InsertData insertData = new InsertData(
                "C:\\Users\\82108\\Desktop\\neonaduri\\S07P22A702\\NEONADURI_back\\src\\main\\resources\\data"
        );
        String[] line = null;
        Classification cls = getClassification();
        System.out.println("start");
        while ((line = insertData.nextRead()) != null) {
            Region reg = getRegion(line);
            Spot spot = getSpot(line, cls, reg);
            getStore(spot,line);
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
            regionRepository.saveAndFlush(region);
        }
        return region;
    }

    @Transactional
    public Spot getSpot(String[] line, Classification cls, Region reg) throws IOException {
        Spot spot = spotRepository.findSpotBySpotName(line[0]);

        if (spot == null) {
            String url = "https://www.google.com/search?q=" + line[0] + "&tbm=isch";
            Connection cn = Jsoup.connect(url);
            Document doc = cn.get();
            String img = doc.select("img.rg_i.Q4LuWd").attr("data-src");

            spot = new Spot(
                    cls.getClassId(),
                    reg.getRegionId(),
                    line[0],
                    Float.parseFloat(line[8]),
                    Float.parseFloat(line[9]),
                    img
            );
            return spotRepository.save(spot);
        }
        return spot;
    }

    @Transactional
    public void getStore(Spot spot, String[] line){
        /* Store 저장 */
        Store store = new Store(spot, line[1], line[2], line[3], line[4]);
        storeRepository.save(store);
        System.out.println("store = " + store);
    }

    @Transactional
    public Classification getClassification(){
        return classificationRepository.findClassificationByMdClassAndSmClass("쇼핑", "시장");
    }
}
