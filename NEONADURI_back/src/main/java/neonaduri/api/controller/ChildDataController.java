package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequiredArgsConstructor
public class ChildDataController {

    private final RegionRepository regionRepository;

    private final ClassificationRepository classificationRepository;

    @GetMapping("/child")
    @Transactional
    public void childFacilityData() {

        BufferedReader br = null;

        try {
            //파일 넣어준다.
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\gus48\\Desktop\\702\\S07P22A702\\data\\childFacility.csv"), "UTF-8"));
            String line = "";
            br.readLine(); //첫 행은 버린다. (컬럼명이므로)

            //파일이 끝날 때까지 돌린다.
            while ((line = br.readLine()) != null) {
                /*
                  *을 기준으로 찢어서 배열에 담아준다.
                  array[0] : 시설 명
                  array[1] : 주소
                  array[2] : 전화번호
                  array[3] : 위도
                  array[4] : 경도
                 */
                String[] array = line.split("\\*");

//regionId 뽑기
//                주소를 띄어쓰기 기준으로 쪼갠다.
//                2번 인덱스가 시/군/구로 끝난다면 시군구가 두 단어, 3번 인덱스가 면
//                그렇지 않다면 2번 인덱스가 면
                String[] address = array[1].split(" ");

                String sido = null;
                
                //시도명 올바르게 넣어줌
                if(address[0].equals("서울")){
                    sido = "서울특별시";
                } else if (address[0].equals("부산") || address[0].equals("대구") || address[0].equals("인천") || address[0].equals("광주") || address[0].equals("대전") || address[0].equals("울산")){
                    sido = address[0] + "광역시";
                } else if (address[0].equals("세종특별자치시") || address[0].equals("제주특별자치도")){
                    sido = address[0];
                } else if (address[0].equals("경기") || address[0].equals("강원")){
                    sido = address[0] + "도";
                } else if (address[0].equals("충북")){
                    sido = "충청북도";
                } else if (address[0].equals("충남")){
                    sido = "충청남도";
                } else if (address[0].equals("경북")) {
                    sido = "경상북도";
                } else if (address[0].equals("경남")) {
                    sido = "경상남도";
                } else if (address[0].equals("전북")) {
                    sido = "전라북도";
                } else if (address[0].equals("전남")) {
                    sido = "전라남도";
                }


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
                    //check가 시군구라면
                    if (check.charAt(check.length() - 1) == '시' || check.charAt(check.length() - 1) == '군' || check.charAt(check.length() - 1) == '구') {
                        sigungu = address[1] + address[2];
                        myeon = address[3];
                    }
                    //check가 시군구가 아니라면
                    else {
                        sigungu = address[1];
                        myeon = address[2];
                    }
                }

                //추출한 시군구, 면으로 지역코드 뽑아낸다.
                Region region = regionRepository.findRegionBySigunguAndMyeon(sigungu, myeon);
                System.out.println(region.getRegionId());

//classification 뽑기
                //spotName 이 지정된 캐릭터명을 포함하는지 확인하여 분류
                //뽀로로, 타요, 폴리, 라바, 기타
                if(array[0].contains("뽀로로")){
                    Classification classification = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "뽀로로");
                } else if(array[0].contains("타요")){
                    Classification classification = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "타요");
                } else if(array[0].contains("폴리")){
                    Classification classification = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "폴리");
                } else if(array[0].contains("라바")){
                    Classification classification = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "라바");
                } else {
                    Classification classification = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
                }





            }


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
