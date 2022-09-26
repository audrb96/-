package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequiredArgsConstructor
public class ChildDataController {

    private final RegionRepository regionRepository;

    private final ClassificationRepository classificationRepository;

    private final SpotRepository spotRepository;

    /**
     * child data 주입
     */
    @GetMapping("/child")
    @Transactional
    public void childFacilityData() {
        int idx = 0;
        BufferedReader br = null;

        try {
            //파일 넣어준다.
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\SSAFY\\Desktop\\neonaduri\\S07P22A702\\data\\childFacility.csv"), "UTF-8"));
            String line = "";
            br.readLine(); //첫 행은 버린다. (컬럼명이므로)

            Classification pororo = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "뽀로로");
            Classification tayo = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "타요");
            Classification exc1 = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
            Classification poly = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "폴리");
            Classification lava = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "라바");


            //이미지
            //뽀로로
            String pororoImage = "spot/GA1.jfif";
            //타요
            String tayoImage = "spot/GB1.jfif";
            //폴리
            String polyImage = "spot/GC1.jfif";
            //라바
            String lavaImage = "spot/GD2.jfif";
            //기타
            String excImage0 = "spot/GE0.jfif";
            String excImage1 = "spot/GE1.jfif";
            String excImage2 = "spot/GE2.jfif";
            String excImage3 = "spot/GE3.jfif";
            String excImage4 = "spot/GE4.jfif";
            String excImage5 = "spot/GE5.jfif";
            String excImage6 = "spot/GE6.jfif";


            //기타이미지를 배열로
            String[] excImage = {excImage0, excImage1, excImage2, excImage3, excImage4, excImage5, excImage6};


            int i = 0; //i를 1씩 증가시켜서 나누기 7한 나머지로 어떤 이미지 넣을지 정하자

            //파일이 끝날 때까지 돌린다.
            while ((line = br.readLine()) != null) {
                i++;

                /*
                  *을 기준으로 찢어서 배열에 담아준다.
                  array[0] : 시설 명
                  array[1] : 주소
                  array[2] : 전화번호
                  array[3] : 위도
                  array[4] : 경도
                 */
                String[] array = line.split("\\*");

/** regionId 뽑기 */
//                주소를 띄어쓰기 기준으로 쪼갠다.
//                2번 인덱스가 시/군/구로 끝난다면 시군구가 두 단어, 3번 인덱스가 면
//                그렇지 않다면 2번 인덱스가 면
                String[] address = array[1].split(" ");

                String sido = address[0];
                String sigungu = null;
                String myeon = null;

                String check = address[2]; //사용하기 쉽게 check 이라는 변수에 담아준다.

                //세종특별자치시일 경우 (데이터에 시군구 내용 없음)
                if (address[0].charAt(0) == '세') {
                    sigungu = "세종시";
                    myeon = address[1];
                }
                //세종시가 아닌 경우
                else {
                    //check가 시군구라면 check 버립니다.
                    if (check.charAt(check.length() - 1) == '시' || check.charAt(check.length() - 1) == '군' || check.charAt(check.length() - 1) == '구') {
                        sigungu = address[1];
                        myeon = address[3];
                    }
                    //check가 시군구가 아니라면 check가 면
                    else {
                        sigungu = address[1];
                        myeon = address[2];
                    }
                }


                //추출한 시군구, 면으로 지역코드 뽑아낸다.
                Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(sido, sigungu, myeon);
                Long regionId = region.getRegionId();


/** classification 뽑기*/
/** 캐릭터라면 캐릭터이미지도 넣어준다.*/
                //spotName 이 지정된 캐릭터명을 포함하는지 확인하여 분류
                //뽀로로, 타요, 폴리, 라바, 기타
                Classification cls;
                String img = null;

                if (array[0].contains("뽀로로")) {
                    cls = pororo;
                    img = pororoImage;
                } else if (array[0].contains("타요")) {
                    cls = tayo;
                    img = tayoImage;
                } else if (array[0].contains("롤리폴리")) {
                    cls = exc1;
                } else if (array[0].contains("아시아폴리스")) {
                    cls = exc1;
                } else if (array[0].contains("폴리")) {
                    cls = poly;
                    img = polyImage;
                } else if (array[0].contains("라바")) {
                    cls = lava;
                    img = lavaImage;
                } else {
                    cls = exc1;
                }

                Long clsId = cls.getClassId();


/** 기타 이미지 추가*/
                if(img == null){
                    img = excImage[i%7];
                }



                Spot spot = new Spot(
                        clsId,
                        Float.parseFloat(array[3]),
                        Float.parseFloat(array[4]),
                        regionId,
                        img,
                        array[0],
                        array[2]

                );
                spotRepository.save(spot);
            }


//            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //스트림 닫아준다.
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
