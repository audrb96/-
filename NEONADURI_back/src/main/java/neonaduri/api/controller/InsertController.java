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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        int idx = 0;
        Classification cls = classificationRepository.findClassificationByMdClassAndSmClass("쇼핑", "시장");

        while ((line = insertData.nextRead()) != null) {
            idx += 1;
//            System.out.println("idx = " + idx);
//            System.out.println("line = " + line);


            Region reg = getRegion(line);
//            System.out.println("reg.toString() = " + reg.toString());

            if (spotRepository.findSpotBySpotName(line[0]) == null){
                String url = "https://www.google.com/search?q=" + line[0] + "&tbm=isch";
                Connection cn = Jsoup.connect(url);
                Document doc = cn.get();
                String img = doc.select("img.rg_i.Q4LuWd").get(20).attr("data-src");

                Spot spot = new Spot(
                        cls.getClassId(),
                        reg.getRegionId(),
                        line[0],
                        Float.parseFloat(line[8]),
                        Float.parseFloat(line[9]),
                        img
                );
                spotRepository.save(spot);
            }

            Spot saveSpot = spotRepository.findSpotBySpotName(line[0]);
//            System.out.println("saveSpot = " + saveSpot.toString());

            Store store = new Store(saveSpot, line[1], line[2], line[3], line[4]);
//            System.out.println("store = " + store.toString());
            storeRepository.save(store);
        }
        return "good!";
    }

    public Region getRegion(String[] line){
        System.out.println("line = " + Arrays.toString(line));
        String[] s = line[6].split(" ");

        if (s.length == 2){
            line[6] = s[0];
        }

        Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(line[5], line[6], line[7]);

        if (region == null){
            Region newRegion = new Region(line[5], line[6], line[7]);
            return regionRepository.save(newRegion);
        }
        return region;
    }
}
